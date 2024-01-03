# Voting App

## Introduction
This project is a Java-based voting application created using JavaFX for the user interface and JDBC with DAO Implementations for database interaction. The application allows users to create polls, vote in polls, and view poll results.

## Features
- User authentication and registration
- Create, edit, and delete polls
- Vote in polls
- View poll results
- Anonymous voting option
- Password encryption for user security

## Technologies Used
- Java 21
- JavaFX for UI
- JDBC for database connectivity
- Scene Builder for UI design
- Maven for dependency management

## Project Structure
The project follows the MVC (Model-View-Controller) design pattern.

### Directories
- `src/main/java/com/example/votingapp/`
    - `DAO`: Contains Data Access Object interfaces.
    - `model`: Contains Java model classes.
    - `DAOImplementations`: Contains DAO Implementations.
    - `Controllers`: Contains Login & Register controllers.
- `src/main/resources`: Contains resource files (e.g., FXML files).

### Classes
- `com.example.votingapp.DAO`: Contains interfaces for DAOs (Data Access Objects).
- `com.example.votingapp.DAOImplementation`: Contains implementations for DAO interfaces.
- `com.example.votingapp.model`: Contains model classes representing entities.
- `com.example.votingapp.controller`: Contains class files crucial for database conncetion.

## Installation
1. Clone the repository: `git clone https://github.com/MohamedElHaddati/VotingApp.git`
2. Open the project in your preferred IDE (such as IntelliJ IDEA).
3. Set up the necessary dependencies (JavaFX, JDBC driver, etc.).
4. Run the application.

## Usage
- Run the application.
- Register/Login to access the voting functionalities.
- Create polls, vote in polls, and view results.

## Database Setup
- Create a MySQL database named `votingapp`.
- Edit the DatabaseConnection.java if your DB connection credentials are different that root and an empty password.
- Use the provided SQL scripts to create tables and insert initial data.
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `Email` (`Email`),
  CONSTRAINT `nn_full_name` CHECK (`full_name` is not null),
  CONSTRAINT `nn_user_name` CHECK (`user_name` is not null),
  CONSTRAINT `nn_Email` CHECK (`Email` is not null)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `poll` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `privateCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `poll_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `nn_user_id` CHECK (`user_id` is not null)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Poll_id` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Poll_id` (`Poll_id`),
  CONSTRAINT `option_ibfk_1` FOREIGN KEY (`Poll_id`) REFERENCES `poll` (`id`),
  CONSTRAINT `nn_Poll_id` CHECK (`Poll_id` is not null)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `poll_id` int(11) NOT NULL,
  `option_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `poll_id` (`poll_id`),
  KEY `option_id` (`option_id`),
  CONSTRAINT `vote_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `vote_ibfk_2` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`id`),
  CONSTRAINT `vote_ibfk_3` FOREIGN KEY (`option_id`) REFERENCES `option` (`id`),
  CONSTRAINT `nn_vote_user_id` CHECK (`user_id` is not null),
  CONSTRAINT `nn_vote_poll_id` CHECK (`poll_id` is not null),
  CONSTRAINT `nn_vote_option_id` CHECK (`option_id` is not null)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

## Contributors
- [EL HADDATI Mohamed](https://github.com/MohamedElHaddati) - Creator
- [Boutbib Ayoub](https://github.com/BoutbibB) - Creator

## License
This project is licensed under the [MIT License](LICENSE).





