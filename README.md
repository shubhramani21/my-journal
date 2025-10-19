# My Journal - Personal Journaling Application

A full-stack web application that allows users to create, manage, and organize their personal journal entries securely.

## 🚀 Live Demo

**[Try it live here](https://my-journal-aseb.onrender.com/login)**

Create an account and start journaling!

---

## ✨ Features

- **🔐 Secure Authentication** - User registration and login with Spring Security and BCrypt password hashing
- **📝 CRUD Operations** - Create, read, update, and delete journal entries
- **👤 User-Specific Data** - Each user has their own private journal entries
- **📱 Responsive Design** - Clean and modern UI built with Bootstrap 5
- **☁️ Cloud Database** - MongoDB Atlas integration for reliable data storage
- **🔒 Session Management** - Secure session handling and logout functionality

---

## 📸 Screenshots

### Login Page
![Login Page](screenshots/login.png)

### Journal Entries Dashboard
![Entries List](screenshots/entries.png)

### Create New Entry
![Create Entry](screenshots/create-entry.png)

---

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3.x** - Java framework for building production-ready applications
- **Spring Security** - Authentication and authorization
- **Spring Data MongoDB** - Database integration
- **BCrypt** - Password encryption
- **Java 17** - Programming language

### Frontend
- **Thymeleaf** - Server-side Java template engine
- **Bootstrap 5** - Modern CSS framework for responsive design
- **JavaScript** - Dynamic functionality and API calls
- **Fetch API** - Client-server communication

### Database
- **MongoDB Atlas** - Cloud-hosted NoSQL database

### Deployment & Tools
- **Render.com** - Cloud platform for deployment
- **Git & GitHub** - Version control
- **Maven** - Dependency management and build tool

---

## 🏗️ Architecture
```
Frontend (Thymeleaf + Bootstrap)
         ↓
Spring Boot Controllers
         ↓
Service Layer
         ↓
Repository Layer (Spring Data MongoDB)
         ↓
MongoDB Atlas (Cloud Database)
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (local or Atlas account)
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/journal-app.git
cd journal-app
```

2. **Configure MongoDB**

Create `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/journaldb
server.port=8080
```

Or use MongoDB Atlas:
```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/journaldb
```

3. **Build the project**
```bash
./mvnw clean install
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

5. **Access the application**

Open your browser and go to: `http://localhost:8080`

---

## 🔐 API Endpoints

### Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/login` | Login page |
| GET | `/signup` | Signup page |
| POST | `/public` | User registration |

### Protected Endpoints (Requires Authentication)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/entries` | View all user's entries |
| GET | `/entries/new` | Create entry form |
| GET | `/entries/edit/{id}` | Edit entry form |
| GET | `/journal` | Get all entries (API) |
| POST | `/journal` | Create new entry (API) |
| GET | `/journal/id/{id}` | Get entry by ID (API) |
| PUT | `/journal/id/{id}` | Update entry (API) |
| DELETE | `/journal/{id}` | Delete entry (API) |

---

## 📦 Project Structure
```
journal-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/Journal/
│   │   │   ├── Config/           # Security configuration
│   │   │   ├── Control/          # Controllers
│   │   │   ├── Entity/           # Data models
│   │   │   ├── Repository/       # Database repositories
│   │   │   └── Service/          # Business logic
│   │   └── resources/
│   │       ├── templates/        # Thymeleaf HTML templates
│   │       └── application.properties
├── screenshots/                   # App screenshots
├── pom.xml                       # Maven dependencies
└── README.md
```

---

## 🔒 Security Features

- **Password Encryption** - All passwords are hashed using BCrypt (12 rounds)
- **Session Management** - Secure session handling with Spring Security
- **CSRF Protection** - Enabled for form submissions
- **Authentication Required** - All journal operations require user login
- **User Isolation** - Users can only access their own journal entries

---

## 🌐 Deployment

The application is deployed on **Render.com** with the following configuration:

### Environment Variables
```
MONGODB_URI=mongodb+srv://...      # MongoDB Atlas connection string
PORT=8080                          # Server port (auto-assigned by Render)
```

### Build Command
```bash
./mvnw clean install -DskipTests
```

### Start Command
```bash
java -jar target/journal-app.jar
```

---

## 🎯 Future Enhancements

- [ ] **Sentiment Analysis** - Analyze mood/sentiment of journal entries
- [ ] **Email Notifications** - Weekly summary emails
- [ ] **Rich Text Editor** - Format entries with markdown or rich text
- [ ] **Search & Filter** - Search entries by keyword or date
- [ ] **Tags & Categories** - Organize entries with custom tags
- [ ] **Export to PDF** - Download entries as PDF
- [ ] **Dark Mode** - Toggle between light and dark themes
- [ ] **Mobile App** - Native mobile application


---

## 📝 Development Notes

### Key Learning Points
- Implemented full-stack authentication with Spring Security
- Integrated MongoDB Atlas for cloud database storage
- Built RESTful API with proper HTTP methods
- Created responsive UI with Bootstrap
- Deployed to production on Render.com
- Managed environment variables for sensitive data
  


This project is open source and available under the [MIT License](LICENSE).

---

**⭐ If you found this project helpful, please give it a star!**
