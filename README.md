# Client Manager API (Seek challenge)

## Description
This is a simple API for managing clients. It allows you to create, read, update, and delete clients. It also allows you generate basic statistics with the clients data.

## About this project

### Back-end server
The back-end server is built with Spring Boot and Java 21. It uses a reactive stack with Spring WebFlux and Spring Data R2DBC. The database used is MySQL, and the API is protected using Spring Security.

The default username and password por login are `root@gmail.com` and `root` respectively.

### Prometheus
Prometheus is used for monitoring the API. It collects metrics from the API and stores them in a time-series database. The metrics can be visualized using Grafana.

### Grafana
Grafana is used for visualizing the metrics collected by Prometheus. It provides a web interface for creating dashboards and visualizing the data. To add the Prometheus data source, go to the Grafana UI and add a new data source. The URL for the Prometheus server is `http://prometheus:9090`.
And to add the dashboard, go to the Grafana UI and import the dashboard using the code `11378`.

## On-Cloud usage
The API is deployed on AWS and can be accessed at the following URL:
```
http://44.210.120.111/
```

### SSH access
You can access the server using SSH with the following command:
```
ssh seek@44.210.120.111
```
With the password `admin-seek`.
To access the directory where the project is located, use the following command:
```
cd /home/ec2-user/seek-client-manager
```

### Back-end server
The back-end server is allocated on port 8080 and can be accessed through Swagger UI at the following URL:
```
http://44.210.120.111:8080/swagger-ui.html
```

### Prometheus
The Prometheus server is allocated on port 9090 and can be accessed at the following URL:
```
http://44.210.120.111:9090
```

### Grafana
The Grafana server is allocated on port 3000 and can be accessed at the following URL:
```
http://44.210.120.111:3000
```
The default username and password are `admin` and `admin-seek` respectively.

## How to run locally
The project can be run locally using Docker. The project is configured to use Docker Compose, which allows you to run the API and the database in separate containers.

### Prerequisites
- Docker
- Docker Compose
- Ports 8080, 9090 and 3000 should be free. If you want to run the project on different ports, you can change the ports in the `compose.yml` file.

### Steps to run the project
- Clone the repository:
```
git clone https://github.com/danieljaraba/seek-client-manager
```
- Navigate to the project directory:
```
cd seek-client-manager
```
- Build and run the project:
```
docker-compose up --build -dV
```
- Access the API at the following URL:
```
http://localhost:8080/swagger-ui.html
```

## Postman collection
The Postman collection for the API is located in the `postman` directory. You can import the collection into Postman to test the API endpoints.

The collection includes 2 environments:
- 000_LOC: for local usage
- 001_DEV: for cloud usage
