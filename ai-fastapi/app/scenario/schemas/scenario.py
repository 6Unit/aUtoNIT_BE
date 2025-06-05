# 요청/응답 DTO
from pydantic import BaseModel

class ScenarioCreate(BaseModel):
    title: str
    description: str | None = None

class ScenarioRead(BaseModel):
    id: int
    title: str
    description: str | None

    class Config:
        orm_mode = True
