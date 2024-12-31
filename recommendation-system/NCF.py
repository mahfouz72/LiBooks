import pandas as pd
from sqlalchemy import create_engine
import torch
import torch.nn as nn
import torch.optim as optim
from sklearn.model_selection import train_test_split
from torch.utils.data import DataLoader, Dataset

# configuring the connection
engine = create_engine('mysql+pymysql://libooks_admin:libooks26@localhost/libooks')

reviews_query = "SELECT user_id, book_id, rating FROM review"

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
    def __init__(self, num_users, num_books, embedding_size=32):
        super(NCF, self).__init__()
        
        self.user_embedding = nn.Embedding(num_users, embedding_size)
        self.book_embedding = nn.Embedding(num_books, embedding_size)

        self.fc1 = nn.Linear(embedding_size * 2, 128)
        self.fc2 = nn.Linear(128, 64)
        self.fc3 = nn.Linear(64, 1)

        self.dropout = nn.Dropout(0.01)

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

def train():
    reviews_data = pd.read_sql(reviews_query, con=engine)

    user_ids = reviews_data['user_id'].unique().tolist()
    book_ids = reviews_data['book_id'].unique().tolist()

    # Constructing a continuous index for the user and book ids for embeddings
    user_id_map = {id: idx for idx, id in enumerate(user_ids)}
    book_id_map = {id: idx for idx, id in enumerate(book_ids)}
    reviews_data['user_id'] = reviews_data['user_id'].map(user_id_map)
    reviews_data['book_id'] = reviews_data['book_id'].map(book_id_map)

    # X => features, y => target
    X = reviews_data[['user_id', 'book_id']].values
    y = reviews_data['rating'].values

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=13)

    train_dataset = ReviewDataset(X_train[:, 0], X_train[:, 1], y_train)
    test_dataset = ReviewDataset(X_test[:, 0], X_test[:, 1], y_test)

    train_loader = DataLoader(train_dataset, batch_size=128, shuffle=True)
    test_loader = DataLoader(test_dataset, batch_size=128, shuffle=False)

    num_users = len(user_ids)
    num_books = len(book_ids)
    model = NCF(num_users, num_books)
    criterion = nn.MSELoss()
    optimizer = optim.Adam(model.parameters(), lr=0.01, weight_decay=1e-5)

    # Training loop
    for epoch in range(100):
        model.train() # Setting the model to training mode
        total_loss = 0
        
        for user_ids, book_ids, ratings in train_loader:
            user_ids = user_ids.long()
            book_ids = book_ids.long()
            ratings = ratings.float()

            optimizer.zero_grad()
            outputs = model(user_ids, book_ids).squeeze() # Removing the extra dimension
            loss = criterion(outputs, ratings)
            loss.backward()
            optimizer.step()
            total_loss += loss.item()

        print(f'Epoch {epoch+1}, Loss: {total_loss/len(train_loader)}')

    return model

def show_predictions():
    
    reviews_data = pd.read_sql(reviews_query, con=engine)

    # Prepare the data
    user_ids = reviews_data['user_id'].unique().tolist()
    book_ids = reviews_data['book_id'].unique().tolist()

    num_users = len(user_ids)
    num_books = len(book_ids)
    model = NCF(num_users, num_books)
    model.load_state_dict(torch.load('ncf.pth'))

    for user_id in range(10):
        for book_id in range(10):
            user_tensor = torch.tensor([user_id])
            book_tensor = torch.tensor([book_id])
            rating = model(user_tensor, book_tensor).item()
            print(f'User {user_id} will rate book {book_id} with {rating} stars')

if __name__ == '__main__':
    model = train()
    torch.save(model.state_dict(), 'recommendation-system/ncf.pth')

    # show_predictions()