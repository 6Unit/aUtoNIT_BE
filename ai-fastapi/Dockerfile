FROM python:3.11-slim

# 기본 시스템 패키지 설치 (jpype1 빌드를 위해)
RUN apt-get update && apt-get install -y \
    gcc \
    g++ \
    libglib2.0-0 \
    git \
    curl \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# requirements.txt 복사 및 설치
COPY requirements.txt .

RUN pip install --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt

# 소스 복사
COPY . .

# FastAPI 앱 실행
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
