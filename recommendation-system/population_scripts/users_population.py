import random
from faker import Faker
from datetime import datetime
import mysql.connector as msqlc

fake = Faker()

# Configure the connection
conn = msqlc.connect(host="localhost", user="libooks_admin", password="libooks26", database="libooks")
cursor = conn.cursor()

def generate_user_data():
    date_created = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    date_of_birth = fake.date_of_birth(minimum_age=18, maximum_age=40).strftime('%Y-%m-%d')
    email = fake.email(domain='gmail.com')
    password = fake.password(length=10, special_chars=True, digits=True, upper_case=True, lower_case=True)
    username = fake.user_name()
    return (date_created, date_of_birth, email, password, username)

for _ in range(999):
    user_data = generate_user_data()
    cursor.execute('''
        INSERT INTO bookworm (date_created, date_of_birth, email, password, username)
        VALUES (%s, %s, %s, %s, %s)'''
        , user_data)
    
conn.commit()
cursor.close()