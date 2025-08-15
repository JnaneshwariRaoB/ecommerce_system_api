API Endpoints
All endpoints are accessible from the base URL: http://localhost:8080/api.
-----------------------------------------------------------------------
1. Product Management 🛍
Handles CRUD operations for products.

Method	Endpoint	Description	Example Body (JSON)

POST	/products	Creates a new product.	{"name": "Laptop Pro", "description": "High-end laptop.", "price": 1200.00, "stockQuantity": 50}
GET	/products	Retrieves a list of all products.	-
GET	/products/{id}	Retrieves a single product by ID.	-
PUT	/products/{id}	Updates an existing product.	{"name": "Laptop Pro (Updated)", "price": 1250.00, "stockQuantity": 45}
DELETE	/products/{id}	Deletes a product by ID.	-

-------------------------------------------------------------------------
2. User Management 
Handles user creation and retrieval.

Method	Endpoint	Description	Example Body (JSON)
POST	/users	Creates a new user.	{"username": "johndoe", "password": "password", "email": "john.doe@example.com"}
GET	/users	Retrieves a list of all users.	-

======================================================================

3. Order Processing 
Handles order creation and payment.

Method	Endpoint	Description	Example Parameters
POST	/orders	Creates a new order.	?userId=1&totalAmount=27.50
POST	/orders/{orderId}/payment	Processes payment for an order.	?paymentMethod=Credit Card
GET	/orders	Retrieves a list of all orders.	-

-----------------------------------------------------------------------------

4. Reviews and Ratings 
Handles creating and retrieving product reviews.

Method	Endpoint	Description	Example Body (JSON)
POST	/reviews/products/{productId}/users/{userId}	Submits a new review for a product.	{"rating": 5, "comment": "Excellent product!"}
GET	/reviews	Retrieves all reviews.	-
GET	/reviews/products/{productId}	Retrieves all reviews for a specific product.
