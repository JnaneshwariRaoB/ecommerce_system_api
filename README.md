Database Configuration: Ensure you have a MySQL server running with a database named ecommerce_db.

Configuration File: Update the src/main/resources/application.properties file with your database credentials.

Run the Application:

From your project's root directory, run the command: mvn spring-boot:run

Alternatively, you can run the Application.java file directly from your IDE.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
API Endpoints
All endpoints are accessible from the base URL: http://localhost:8080/api.
Most endpoints require Basic Authentication.

Admin User: username: admin, password: adminpass

Standard User: username: user, password: userpass

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1. Admin Endpoints  (Requires ADMIN Role)
These endpoints are for managing the system and require admin:adminpass credentials.

Method	Endpoint	Description	Example Body (JSON)
POST	/products	Creates a new product.	{"name": "Laptop Pro", "description": "High-end laptop.", "price": 1200.00, "stockQuantity": 50}

GET	/products	Retrieves a list of all products.	-

GET	/products/{id}	Retrieves a single product by ID.	-

PUT	/products/{id}	Updates an existing product.	{"name": "Laptop Pro (Updated)", "price": 1250.00, "stockQuantity": 45}

DELETE	/products/{id}	Deletes a product by ID.	-

GET	/users	Retrieves a list of all users.	-


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

2. Public Endpoints (No Authentication Required)
These endpoints are open to all and do not require credentials.

Method	Endpoint	Description	Example Body (JSON)

POST	/users	Creates a new user.	{"username": "johndoe", "password": "securepassword123", "email": "john.doe@example.com"}

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3. User Endpoints  (Requires USER or ADMIN Role)
These endpoints are for general use and require authentication as either a user or admin.

Method	Endpoint	Description	Example Data

POST	/orders	Creates a new order.	?userId=1&totalAmount=27.50 (sent via URL)

POST	/orders/{orderId}/payment	Processes payment for an order.	?paymentMethod=Credit Card (sent via URL)

GET	/orders	Retrieves a list of all orders.	-

POST	/reviews/products/{productId}/users/{userId}	Submits a new review for a product.	{"rating": 5, "comment": "Excellent product!"} (sent via JSON body)

GET	/reviews	Retrieves all reviews.	-

GET	/reviews/products/{productId}	Retrieves all reviews for a specific product.
