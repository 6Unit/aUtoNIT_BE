from fastapi import FastAPI
from app.scenario.routers import scenario

app = FastAPI()

app.include_router(scenario.router)
