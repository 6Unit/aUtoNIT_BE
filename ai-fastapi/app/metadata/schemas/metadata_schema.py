from pydantic import BaseModel
from typing import List, Dict, Any
from datetime import datetime

class MetadataIn(BaseModel):
    project_id: str
    metadata_json: List[Dict[str, Any]]  # ✅ 리스트 형태로 변경

class MetadataOut(MetadataIn):
    analyzed_at: datetime
