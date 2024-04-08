# SER516-Team-Denver

## Taiga API Integration

This project is a Java application for interacting with the Taiga API to perform various task and calculating metrics.

## Application is deployed as a whole on - http://54.89.25.90:8080/

## Setting up the application

### 1) Clone the repository

   ```bash
   git clone https://github.com/ser516asu/SER516-Team-Denver.git
   cd SER516-Team-Denver
   ```

### 2) Run docker-compose

Go to the project root and run the JAR file

```bash
cd Microservices
docker-compose up
```

IMPORTANT NOTE: 
Some metrics like Partial running sum and cycle time take some time to load, so please wait for 10-15 seconds for those metrics to be displayed properly.
