import random
from datetime import datetime, timedelta
import mysql.connector as msqlc

# Configure the connection
conn = msqlc.connect(host="localhost", user="libooks_admin", password="libooks26", database="libooks")
cursor = conn.cursor()

review_texts = [
    "This book is amazing!",
    "I loved this book!",
    "I hated this book!",
    "This book is boring!",
    "This book is interesting!"
    "Amazing book, highly recommend!",
    "Not my cup of tea, but well-written.",
    "A decent read with some interesting points.",
    "Couldn't put it down, fantastic!",
    "Too slow for my taste, but others might enjoy it.",
    "Loved the characters and the plot twists.",
    "An okay book, but not memorable.",
    "Great insights and very informative.",
    "A bit boring, but had some good moments.",
    "One of the best books I've read this year.",
    "A must-read for everyone!",
    "I couldn't get into this book at all.",
    "A bit too long, but worth the read.",
    "I couldn't stand the main character.",
    "The ending was a letdown."
    "I couldn't get past the first chapter.",
    "The writing style was too dry for me.",
    "I loved the setting and the atmosphere.",
    "The pacing was all over the place.",
    "I was hooked from the first page."
    "The plot was predictable, but still enjoyable.",
    "I didn't care for the writing style.",
    "The characters felt real and relatable.",
    "I couldn't stop thinking about this book.",
    "I wish there was a sequel!"
]

def random_date(start, end):
    return start + timedelta(
        seconds=random.randint(0, int((end - start).total_seconds()))
    )

not_available_books = [57, 95, 96, 98]

reviews = []
start_date = datetime(2024, 1, 1)
end_date = datetime(2024, 12, 31)
ratings = [0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5]

for user_id in range(1, 1000):
    reviewd_books = set()
    for _ in range(random.randint(1, 30)):
        book_id = random.randint(51, 99)
        while book_id in not_available_books:
            book_id = random.randint(51, 99)
        if book_id in reviewd_books:
            continue

        reviewd_books.add(book_id)
        review = {
            "date": random_date(start_date, end_date).strftime('%Y-%m-%d %H:%M:%S'),
            "rating": random.choice(ratings),
            "review_text": random.choice(review_texts),
            "book_id": book_id,
            "user_id": user_id
        }
        reviews.append(review)

for review in reviews:
    cursor.execute('''
        INSERT INTO review (date, rating, review_text, book_id, user_id)
        VALUES (%s, %s, %s, %s, %s)'''
        , (review["date"], review["rating"], review["review_text"], review["book_id"], review["user_id"]))
    
conn.commit()
cursor.close()