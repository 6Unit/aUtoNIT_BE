# DB 로직
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from app.scenario.models.scenario import Scenario
from app.scenario.schemas.scenario import ScenarioCreate

async def create_scenario(db: AsyncSession, scenario: ScenarioCreate):
    new_scenario = Scenario(**scenario.dict())
    db.add(new_scenario)
    await db.commit()
    await db.refresh(new_scenario)
    return new_scenario

async def get_scenarios(db: AsyncSession):
    result = await db.execute(select(Scenario))
    return result.scalars().all()
