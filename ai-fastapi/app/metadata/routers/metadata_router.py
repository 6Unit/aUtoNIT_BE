from fastapi import APIRouter, HTTPException
from app.metadata.schemas.metadata_schema import MetadataIn
from app.metadata.cruds.metadata_crud import save_metadata, get_metadata

router = APIRouter(prefix="/api/metadata", tags=["metadata"])

@router.post("/save")
def save_ui_metadata(data: MetadataIn):
    save_metadata(data)
    return {"message": "success"}

@router.get("")
def get_ui_metadata(projectId: str):
    result = get_metadata(projectId)
    if not result:
        raise HTTPException(status_code=404, detail="Metadata not found")
    return result
