from pymongo import MongoClient
import os
from dotenv import load_dotenv

load_dotenv()

MONGO_URI = os.getenv("MONGO_URI")
client = MongoClient(MONGO_URI)
mongo_db = client["test_auto"]

# MongoDB 연결
def get_mongo_collection(name: str):
    return mongo_db[name] 
