import numpy as np
import pandas as pd
import pickle as pkl
from sqlalchemy import create_engine
from scipy.sparse.linalg import svds
from sklearn.impute import SimpleImputer, KNNImputer

# configuring the connection
engine = create_engine('mysql+pymysql://libooks_admin:libooks26@localhost/libooks')

reviews_query = "SELECT user_id, book_id, rating FROM review"

def train():
    reviews_data = pd.read_sql(reviews_query, con=engine)

    # Create a pivot table
    pivot_table = reviews_data.pivot_table(index='user_id', columns='book_id', values='rating')

    # imputer = SimpleImputer(strategy='mean')
    imputer = KNNImputer(n_neighbors=5)
    pivot_table = pd.DataFrame(imputer.fit_transform(pivot_table), index=pivot_table.index, columns=pivot_table.columns)
    
    # Convert the pivot table to a matrix
    pivot_matrix = pivot_table.values

    # Perform Singular Value Decomposition
    U, sigma, Vt = svds(pivot_matrix, k=15)

    # Convert sigma to a diagonal matrix
    sigma = np.diag(sigma)

    # Reconstruct the matrix
    predicted_ratings = np.dot(np.dot(U, sigma), Vt)

    # Save the model
    with open('recommendation-system/model.pkl', 'wb') as file:
        pkl.dump(predicted_ratings, file)

    print('Model trained successfully')
    print('Predicted ratings:')
    print(predicted_ratings)
    print('original ratings:')
    print(pivot_matrix)

def recommend():
    pass

if __name__ == '__main__':
    train()