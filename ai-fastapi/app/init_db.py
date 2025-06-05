# app/init_db.py
import asyncio
from app.core.db import engine
from app.core.base import Base

# 모델 import
from app.scenario.models.scenario import Scenario

async def init_models():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)

if __name__ == "__main__":
    asyncio.run(init_models())
