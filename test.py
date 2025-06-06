from sqlalchemy import create_engine, text

username = "skala_user"
password = "skalapass"
host = "localhost"
port = 3306
database = "test_auto"

url = f"mysql+pymysql://{username}:{password}@{host}:{port}/{database}"

try:
    engine = create_engine(url)
    with engine.connect() as conn:
        result = conn.execute(text("SELECT 1"))
        print("✅ DB 연결 성공:", result.scalar())
except Exception as e:
    print("❌ DB 연결 실패:", e)
