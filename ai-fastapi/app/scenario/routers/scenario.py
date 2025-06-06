# API 앤드포인트
from fastapi import APIRouter, Depends
from sqlalchemy.ext.asyncio import AsyncSession
from app.core.db import get_db
from app.scenario.schemas.scenario import ScenarioCreate, ScenarioRead
from app.scenario.cruds import scenario as crud

router = APIRouter(prefix="/api/scenario", tags=["Scenario"])

@router.post("/", response_model=ScenarioRead)
async def create_scenario_api(scenario: ScenarioCreate, db: AsyncSession = Depends(get_db)):
    return await crud.create_scenario(db, scenario)

@router.get("/", response_model=list[ScenarioRead])
async def get_all_scenarios_api(db: AsyncSession = Depends(get_db)):
    return await crud.get_scenarios(db)
