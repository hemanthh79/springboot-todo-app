📝 Spring Boot ToDo App

A full-stack ToDo List web application built using Spring Boot, Thymeleaf, and MySQL.
This app allows users to securely register, log in, and manage their daily tasks with priority and due date tracking.

🚀 Features<br>
🔐 User Authentication – Custom login and registration with Spring Security<br>
➕ Add Tasks – Create tasks with title, description, priority, due date, and type<br>
✏️ Edit / Delete Tasks – Update or remove tasks easily<br>
🗂️ Priority Levels – High, Medium, and Low priority support<br>
📅 Due Date Tracking – Keep tasks on schedule<br>
📊 User Dashboard – View today’s and upcoming tasks<br>
📱 Responsive Design – Clean UI built with Thymeleaf & CSS<br>
🔄 Flash Messages – Success and error notifications for user actions<br>

🛠️ Tech Stack
Backend: Java, Spring Boot, Spring Security, Spring Data JPA

Frontend: Thymeleaf, HTML, CSS, JavaScript

Database: MySQL

Build Tool: Maven

⚡ Getting Started<br>
1️⃣ Clone the Repository<br>
git clone https://github.com/hemanthh79/springboot-todo-app.git<br>
cd springboot-todo-app<br>

2️⃣ Configure Database<br>
Create a MySQL database (e.g., todo_db)<br>

Update your application.properties with DB credentials:

properties :<br>
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db<br>
spring.datasource.username=root<br>
spring.datasource.password=yourpassword<br>

3️⃣ Run the Application<br>
mvn spring-boot:run<br>

4️⃣ Access the App
Visit 👉 http://localhost:8080 in your browser.

📌 Future Enhancements<br>
✅ Add task categories and tags<br>
✅ Implement email reminders for due tasks<br>
✅ Add dark mode support<br>

🤝 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you’d like to change.

📧 Contact<br>
Hemanth Popuri<br>
📍 LinkedIn: www.linkedin.com/in/popuri-hemanth



