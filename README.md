# Caffeinated - Beverage E-commerce Backend

Caffeinated is a sophisticated backend application designed to power a fictional beverage (coffee/tea) e-commerce platform. Developed in Java using the Spring Boot framework, Spring Data JPA, and Spring Security, Caffeinated offers a secure, scalable, and extensible solution for managing the backend operations of an online store. Leveraging a MySQL relational database for data storage, this monolithic application serves as the foundation for an exceptional shopping experience.

## Features

### User Management

1. **User Registration:**

   - Users can seamlessly register for an account, providing the necessary details to personalize their shopping journey.

2. **Authentication and Authorization:**
   - Spring Security is integrated for robust authentication and authorization, ensuring secure access control for users.

### Product Catalog

3. **Product Browsing:**

   - Users can explore a diverse catalog of beverage products, each categorized as tea or coffee, facilitating efficient product discovery.

4. **Category API:**
   - A dedicated category API enables the frontend to filter products based on beverage categories, offering a dynamic and user-friendly shopping experience.

### Cart Management

5. **Shopping Cart Operations:**
   - Users can add products to their cart, remove items, and retrieve detailed information about the contents of their cart.

### Admin Dashboard

6. **Admin Access Management:**
   - Administrators have access to a comprehensive dashboard to grant or retrieve admin privileges for users, ensuring controlled access.

### Product Management

7. **Product CRUD Operations with Images:**
   - Admins can effortlessly add or remove beverage products, including product images, enhancing the visual appeal of the catalog.

### User Profile Management

8. **User and Admin Profile Updates:**
   - Users and administrators can conveniently update their profiles, ensuring accurate and relevant information.

## Microservices Division

Caffeinated has undergone a microservices transformation for improved scalability and maintainability. The following microservices now handle specific functionalities:

### Caffeinated-Persona-Service

**Controllers:**

- AdminController
- UserProfileController

**Responsibilities:**

- Admin tasks (give/retrieve admin access).
- User profile updates.
- CRUD operations on User, user's cart, and user's profile.

### Product-Craftsman-Service

**Controllers:**

- ProductController
- CategoryController

**Responsibilities:**

- CRUD operations on product details.
- Product categorization.

### Cart-Expresso-Service

**Controllers:**

- CartController
- ContactController

**Responsibilities:**

- Manage shopping cart operations.
- Handle user messages and admin responses.
  **Fun Fact:**
  - This microservice is affectionately named "Expresso" (with an 'x') because, much like a shot of espresso kicks up your energy, it's here to bring a shot of excitement to our e-commerce world. The name is a playful nod to the expressive nature of its contact endpoint. After all, where else can users passionately express themselves about their favorite caffeine-infused delights? So, take a sip, err, click, and let the expressive journey begin!

## Ongoing Enhancements

Caffeinated is a dynamic project with ongoing enhancements to meet evolving requirements. Here are some upcoming features:

- **OAuth2 for Authorization:**

  - The addition of OAuth2 will enhance the authorization mechanism, providing a secure and standardized approach.

- **OpenID for Authentication:**

  - Integration of OpenID for authentication, ensuring a seamless and secure login experience for users.

- **Containerization with Docker:**
  - The recent addition of Docker using Google Jib facilitates easy deployment and management of the application in containerized environments.

You can find the enhancement details in the enhancement-info.txt file.
Contributions and feedback are welcome. Feel free to open issues, suggest enhancements, or submit pull requests. Join us in making Caffeinated the ultimate backend solution for beverage e-commerce!

Cheers!
The Caffeinated Team
