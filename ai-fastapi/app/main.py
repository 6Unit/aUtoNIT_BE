from fastapi import FastAPI
from app.metadata.routers import metadata_router

app = FastAPI()

app.include_router(metadata_router.router)
