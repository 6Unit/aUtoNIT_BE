import asyncio
from sqlalchemy import text  
from app.core.db import get_db

async def test_connection():
    async for session in get_db():
        result = await session.execute(text("SELECT 1"))  
        print("✅ MariaDB 연결 성공:", result.scalar())

if __name__ == "__main__":
    asyncio.run(test_connection())
