#InventoryX - Full Stack Inventory Management System 🛒

Streamlining Inventory, Empowering Your Business 💼
Welcome to InventoryX, a modern, responsive full-stack Inventory Management System. Currently, this project is a work in progress, with the backend mostly completed and the frontend still under development. This system will allow businesses to efficiently manage their inventory, products, and orders.

##Features (Implemented & Upcoming) 🚀
Implemented:
Product Management: Add, view, update, and delete products.
Order Management: Basic functionality for order processing.
Search & Sorting: Search products by name/category and sort by price/quantity/name.
Stock Alerts: Notification for low-stock products.
Image Upload: Upload and display product images.
User Authentication: Secure login/signup (admin-only for now).
Soft Delete: Mark products as deleted without removing them from the database.

Upcoming:
Responsive Frontend: Still under development using Angular.
Advanced Features: Category Management, Pagination, Sorting, and more.
Full Dashboard: Stats for low stock alerts, product counts, and other metrics.
User Role Management: Admin access, with future functionality for different user roles.
Frontend Design Tweaks: Enhancements to UI/UX, including more animations and a polished theme.

##Backend Folder Structure 📂
<pre>
src/
├── main/
│   └── java/com/inventoryx/
│       ├── config/                 # Configuration Classes
│       ├── controller/             # REST Controllers
│       ├── dto/                    # Data Transfer Objects
│       ├── model/                  # Database Entities
│       ├── repository/             # JPA Repositories
│       ├── service/                # Business Logic
│       ├── security/               # Spring Security
│       └── exception/              # Custom Exceptions
└── InventoryXApplication.java     # Main Class
</pre>
  
##Frontend Folder Structure 📂
<pre>
src/
├── app/
│   ├── components/                # UI Components (Header, Footer, Sidebar)
│   ├── auth/                      # Authentication (Login, Register)
│   ├── products/                  # Product Management
│   ├── orders/                    # Order Management
│   ├── dashboard/                 # Admin Dashboard
│   ├── models/                    # TypeScript Models
│   ├── services/                  # Services (API calls)
│   └── shared/                    # Shared Modules
├── assets/                        # Images & Styles
├── environments/                  # Environment Settings
└── index.html                     # Entry Point
</pre>

##Technologies Used ⚙️
Backend: Java Spring Boot, Spring Security, JPA, PostgreSQL
Frontend: Angular (LTS), HTML5, CSS3, JavaScript
Database: PostgreSQL
Authentication: JWT (JSON Web Tokens)

##How to Run the Backend 🏃‍♂️
Clone the repository:
```bash
Copy code
git clone https://github.com/SudeepSRathod/Inventory_management_system.git'''
```
Navigate to the backend folder and open it in your IDE (Spring Tool Suite).
Ensure you have PostgreSQL installed and set up a database.
Configure the application.properties file to connect to your PostgreSQL database.

Run the Spring Boot application:
```bash
Copy code
mvn spring-boot:run
```

How to Run the Frontend 🖥️
Clone the repository:
```bash
Copy code
git clone https://github.com/SudeepSRathod/Inventory_management_system.git
```

Navigate to the frontend folder and install dependencies:
```bash
Copy code
cd frontend
npm install
```

Run the Angular application:
```bash
Copy code
ng serve
```

##Running the Full Stack 🛠️
Start the backend (Spring Boot application).
Start the frontend (Angular application).
Open your browser and go to http://localhost:4200 to access the app.

##Contributing 🤝
This project is still in its early stages, and contributions are more than welcome! You can help by:
Submitting issues for bugs or feature requests.
Providing feedback on the frontend design and functionality.
Suggesting improvements or new features.


This project is open-source and available under the MIT License. See the LICENSE file for more details.

##Acknowledgements 🙏
Special thanks to the open-source community for making this project possible!
Designed with a modern and professional color theme to enhance user experience.
Stay tuned for updates as this project continues to evolve! 🎉
