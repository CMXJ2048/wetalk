# WeTalk - Online Social Application

## Overview
WeTalk is an online social application designed to facilitate communication and interaction among users. Built using Java and Spring Boot, WeTalk provides a platform for user authentication, profile management, and social interactions.

## Features
- User registration and authentication
- Profile management
- Secure communication
- Responsive design

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Thymeleaf (for templates)
- Maven (for dependency management)
- H2 Database (for development)

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code)

### Installation
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd wetalk
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```

### Running the Application
To run the application, execute the following command:
```
mvn spring-boot:run
```
The application will start on `http://localhost:8080`.

### Database Initialization
The application uses an H2 in-memory database for development. The database schema is initialized using the SQL script located at `src/main/resources/db/migration/V1__init.sql`.

## Usage
- Access the application through your web browser at `http://localhost:8080`.
- Register a new account or log in with existing credentials to start using the application.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.