# SER516-Team-Denver

## Taiga API Integration

This project is a Java application for interacting with the Taiga API to perform various task and calculating metrics.


## Setting up the application

### 1) Clone the repository


   ```bash
   git clone https://github.com/ser516asu/SER516-Team-Denver.git
   cd SER516-Team-Denver
   ```

### 2) Compile and Run the application

#### Compile and Run the backend
Go to the project root and compile the Maven project

```bash
   mvn compile spring:boot-run
   ```

#### Run the frontend


```bash
   mvn compile exec:java -Dexec.mainClass=org.example.JavaTaigaCode.Main
   
   cd denver_agile_metric_calc
   npm start
   ```
