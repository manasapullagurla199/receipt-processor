# Receipt Processor API

## Overview
A Spring Boot application that processes receipts and calculates points based on specific rules.

## API Endpoints
1. **POST /receipts/process**
    - **Request**:
      ```json
      {
          "retailer": "Target",
          "purchaseDate": "2022-01-02",
          "purchaseTime": "13:13",
          "total": "1.25",
          "items": [
              {"shortDescription": "Pepsi - 12-oz", "price": "1.25"}
          ]
      }
      ```
    - **Response**:
      ```json
      { "id": "123e4567-e89b-12d3-a456-426614174000" }
      ```

2. **GET /receipts/{id}/points**
    - **Response**:
      ```json
      { "points": 31 }
      ```

## Steps to Run the Application with Docker
1. Build the Docker image:
   docker build -t receipt-processor .
2. Run the Docker container:
   docker run -p 8080:8080 receipt-processor

## Steps to Run locally
1. Run the application directly using Java:

   java -jar build/libs/receipt-processor-0.0.1-SNAPSHOT.jar

## Testing the Application

   - **Run Unit Tests**
   : 1. To run the unit tests, execute the following command: ./gradlew test