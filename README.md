# Table reservation
A RESTful web service to manage table reservations.

It is possible to create a table, create reservations for a table and get all the reservations of a given table.


## Requirements

Java version : 1.8         
Maven version : 3.5.0

## Run the project

````
cd path/to/folder

// Clone the project
git clone https://github.com/Jouda-Hidri/TableReservation.git

// Build and test the application
mvn clean install

// Test the application
mvn test

// Run
mvn spring-boot:run

// Create a table
curl -d '{"name": "Table with a view to the mountains"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/table

// Create a reservation (start time and end time are timestamps in seconds)
curl -d '{"customerName": "Mr. Smith", "startTime": 1526151600, "endTime": 1526155200}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/table/{id}/reservation

// Get reservations
curl http://localhost:8080/api/v1/table/{id}
````
