import pandas as pd
import mysql.connector as msqlc
from torch import nn, optim, torch
from sqlalchemy import create_engine
from torch.utils.data import DataLoader, Dataset
from sklearn.model_selection import train_test_split

class ReviewDataset(Dataset):
    def __init__(self, user_ids, book_ids, ratings):
        self.user_ids = user_ids
        self.book_ids = book_ids
        self.ratings = ratings

    def __len__(self):
        return len(self.ratings)

    def __getitem__(self, idx):
        return self.user_ids[idx], self.book_ids[idx], self.ratings[idx]

class NCF(nn.Module):
    def __init__(self, num_users, num_books, embedding_size=64):
        super(NCF, self).__init__()
        
        self.user_embedding = nn.Embedding(num_users, embedding_size)
        self.book_embedding = nn.Embedding(num_books, embedding_size)

        self.fc1 = nn.Linear(embedding_size * 2, 64)
        self.fc2 = nn.Linear(64, 32)
        self.fc3 = nn.Linear(32, 1)

        self.dropout = nn.Dropout(0.1)

        self.silu = nn.SiLU()
        self.relu = nn.ReLU()

    def forward(self, user_ids, book_ids):

        user_embeds = self.user_embedding(user_ids)
        book_embeds = self.book_embedding(book_ids)
        
        x = torch.cat([user_embeds, book_embeds], dim=1)
        x = self.fc1(x)
        x = self.silu(x)

        x = self.dropout(x)

        x = self.fc2(x)
        x = self.silu(x)

        x = self.dropout(x)

        x = self.fc3(x)
        x = self.relu(x)

        return x

def train(reviews_data, user_ids, book_ids):

    # X => features, y => target
    X = reviews_data[['user_id', 'book_id']].values
    y = reviews_data['rating'].values

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.13, random_state=13)

    train_dataset = ReviewDataset(X_train[:, 0], X_train[:, 1], y_train)
    test_dataset = ReviewDataset(X_test[:, 0], X_test[:, 1], y_test)

    train_loader = DataLoader(train_dataset, batch_size=32, shuffle=True)
    test_loader = DataLoader(test_dataset, batch_size=32, shuffle=False)

    num_users = len(user_ids)
    num_books = len(book_ids)
    model = NCF(num_users, num_books)
    criterion = nn.MSELoss()
    optimizer = optim.Adam(model.parameters(), lr=0.01, weight_decay=1e-5)

    best_val_loss = float('inf')
    patience = 10
    patience_counter = 0

    # Training loop
    for epoch in range(50):
        model.train()
        total_loss = 0
        for user_ids, book_ids, ratings in train_loader:
            user_ids = user_ids.long()
            book_ids = book_ids.long()
            ratings = ratings.float()

            optimizer.zero_grad()
            outputs = model(user_ids, book_ids).squeeze()
            loss = criterion(outputs, ratings)
            loss.backward()
            optimizer.step()
            total_loss += loss.item()

        val_loss = 0
        model.eval()
        with torch.no_grad():
            for user_ids, book_ids, ratings in test_loader:
                user_ids = user_ids.long()
                book_ids = book_ids.long()
                ratings = ratings.float()
                outputs = model(user_ids, book_ids).squeeze()
                loss = criterion(outputs, ratings)
                val_loss += loss.item()

        val_loss /= len(test_loader)
        print(f'Epoch {epoch+1}, Loss: {total_loss/len(train_loader)}, Validation Loss: {val_loss}')

        if val_loss < best_val_loss:
            best_val_loss = val_loss
            patience_counter = 0
        else:
            patience_counter += 1

        if patience_counter >= patience:
            print("Early stopping")
            break

    return model

if __name__ == '__main__':

    # configuring the connection
    engine = create_engine('mysql+pymysql://libooks_admin:libooks26@localhost/libooks')

    reviews_query = "SELECT user_id, book_id, rating FROM review"

    reviews_data = pd.read_sql(reviews_query, con=engine)

    user_ids = reviews_data['user_id'].unique().tolist()
    book_ids = reviews_data['book_id'].unique().tolist()

    # Constructing a continuous index for the user and book ids for embeddings
    user_id_map = {id: idx for idx, id in enumerate(user_ids)}
    book_id_map = {id: idx for idx, id in enumerate(book_ids)}

    reviews_data['user_id'] = reviews_data['user_id'].map(user_id_map)
    reviews_data['book_id'] = reviews_data['book_id'].map(book_id_map)

    model = train(reviews_data, user_ids, book_ids)

    # configuring the connection
    conn = msqlc.connect(host="localhost", user="libooks_admin", password="libooks26", database="libooks")
    cursor = conn.cursor()

    # truncate recommendations table
    cursor.execute('TRUNCATE TABLE recommendation;')
    conn.commit()

    # get top 10 unreviewed before recommendations per user
    for user_id in user_ids:
        user_idx = user_id_map[user_id]
        recommended_books = []
        user_reviewed_books = reviews_data[reviews_data['user_id'] == user_idx]['book_id'].values
        for book_id in book_ids:
            book_idx = book_id_map[book_id]
            # checking if user has not reviewed the book
            if book_idx not in user_reviewed_books:
                rating = model(torch.tensor([user_idx]), torch.tensor([book_idx])).item()
                recommended_books.append((book_id, rating))
            
        # get top 10 books with highest ratings
        recommended_books = sorted(recommended_books, key=lambda x: x[1], reverse=True)[:10]
        for rec in recommended_books:
            cursor.execute('''
                INSERT INTO recommendation (book_id, user_id)
                VALUES (%s, %s)'''
                , (rec[0], user_id))
            
    conn.commit()
    cursor.close()