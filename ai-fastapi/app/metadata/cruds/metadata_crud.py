from app.core.mongo import get_mongo_collection
from app.metadata.schemas.metadata_schema import MetadataIn
from datetime import datetime

collection = get_mongo_collection("metadata")

def save_metadata(data: MetadataIn):
    collection.update_one(
        {"project_id": data.project_id},
        {
            "$set": {
                "metadata_json": data.metadata_json,
                "analyzed_at": datetime.utcnow()
            }
        },
        upsert=True
    )

def get_metadata(project_id: str):
    return collection.find_one({"project_id": project_id}, {"_id": 0})
