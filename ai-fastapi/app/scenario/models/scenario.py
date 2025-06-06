# DB 모델
from sqlalchemy import Column, Integer, String, Text
from app.core.base import Base  # 이미 Base 선언된 곳에서 import 해야 함

class Scenario(Base):
    __tablename__ = "scenario"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String(100), nullable=False)
    description = Column(Text, nullable=True)
