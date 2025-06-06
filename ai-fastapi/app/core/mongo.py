from pymongo import MongoClient
import os
from dotenv import load_dotenv

load_dotenv()

MONGO_URI = os.getenv("MONGO_URI")
client = MongoClient(MONGO_URI)
mongo_db = client["test_auto"]  # 사용할 DB 이름

# MongoDB 연결
def get_log_collection():
    return mongo_db["test_logs"]  # 사용할 collection 이름
