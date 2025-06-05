from app.core.mongo import get_log_collection

def test_mongo():
    collection = get_log_collection()
    doc = {"test": "연결확인"}
    result = collection.insert_one(doc)
    print("✅ MongoDB 연결 성공:", result.inserted_id)

if __name__ == "__main__":
    test_mongo()
