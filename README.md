# Threat Intelligence Report Search
A web application for parsing, indexing, and searching digital forensic PDF reports in the Serbian language (Latin and Cyrillic scripts). PDF originals are stored in _MinIO_, while extracted metadata and full-text content are indexed in _Elasticsearch_ for fast search, highlighting, analytics, and visual exploration in _Kibana_.

This repository also includes:
- Sample reports used for testing and demonstration.
- Report templates in both Cyrillic and Latin (Serbian).

## Key Features
1. Parsing & Indexing
   - Upload PDF reports and store originals in _MinIO_.
   - Extract structured metadata (e.g., forensic analyst, CERT organization, threat name, classification, hash, location/address) and index it into _Elasticsearch_.
   - Review and correct extracted values before confirming indexing.
     
3. Search
   - Semi-structured search (e.g., analyst + hash + classification, threat name / organization).
   - Full-text search across report content with highlighted snippets.
   - Combined boolean queries (AND / OR / NOT) and phrase queries.
   - Semantic kNN search using embeddings.
   - Geo-distance search based on indexed location/address data.
  
5. _Kibana_ Visualizations
   - Dashboards for visual exploration and analytics over indexed reports using the _ELK_ stack (_Elasticsearch_ + _Logstash_ + _Kibana_).

## Tech Stack
- Backend: _Java Spring Boot_
- Frontend: _React (Next.js)_
- Storage: _MinIO_, _PostgreSQL_
- Search/Analytics: _Elasticsearch_, _Logstash_, _Kibana_

## Prerequisites
- _Docker_ & _Docker Compose_
- _Java 17+_
- _Node.js 18+_

## Running the Project

> **Note:** This project requires environment configuration files:
> - a `.env` file for Docker (used by `docker-compose.yml`)
> - an additional `.env` (or equivalent config) for the backend inside `/resources`

1. **Start infrastructure** (_MinIO_, _PostgreSQL_, _PgAdmin_, _Elasticsearch_, _Logstash_, _Kibana_)
   - From the repository root (where `docker-compose.yml` is located):

     ```bash
     docker compose up -d
     ```

2. **Run backend**
   - From the backend folder:

     ```bash
     ./mvnw spring-boot:run
     ```

   - Or on Windows:

     ```bash
     mvnw.cmd spring-boot:run
     ```

3. **Run frontend**
   - From the frontend folder:

     ```bash
     npm install
     npm run dev
     ```

## _Kibana_ Dashboards (Import)
This repository contains exported _Kibana_ saved objects. You can import them with:
1. Open _Kibana_
2. Go to Stack Management → Saved Objects
3. Click Import and select the provided .ndjson file

## Repository Content
- `reports/` - sample PDF reports for testing/demo & report templates (Serbian Cyrillic and Latin)
- `kibana/` – exported _Kibana_ dashboards
