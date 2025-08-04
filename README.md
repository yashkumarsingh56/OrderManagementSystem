📦 Order Management System
A full-stack web application for managing customer orders. Built using Spring Boot, React.js, and integrated with AWS services (DynamoDB, S3, SNS). Users can create, view, update, and delete orders, along with uploading PDF invoices.

🔧 Tech Stack
Frontend: React.js (with basic CSS styling)

Backend: Spring Boot (Java)

Database: AWS DynamoDB

File Storage: AWS S3

Notification: AWS SNS

Others: Docker (optional), Swagger, Postman-tested APIs

✅ Features
Create new orders with invoice PDF upload

Edit existing orders

Delete orders

View all orders with metadata

View attached invoice files (PDFs)

Stores order data in AWS DynamoDB

Uploads invoice files to AWS S3

Sends email notification via AWS SNS on new order creation

📂 Folder Structure
OrderManagementSystem/
│
├── backend/ (Spring Boot app)
│   ├── config/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── application.properties
│
├── order-management-frontend/ (React app)
│   ├── App.js
│   ├── App.css
│   └── ...
🚀 How to Run Locally
Backend (Spring Boot)
Install Java 17 and Maven

Configure AWS credentials (~/.aws/credentials)

Run DynamoDB Local:
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
Navigate to backend folder:

cd OrderManagementSystem
mvn spring-boot:run
Frontend (React)
Navigate to frontend folder:
cd order-management-frontend
npm install
npm start
🧪 API Testing (Postman)
POST /orders/upload → Create order with file

PUT /orders/{id} → Update order

GET /orders → List all orders

DELETE /orders/{id} → Delete order


📜 License
This project is for educational/demo purposes.
