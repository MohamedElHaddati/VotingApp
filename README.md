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
- Java 8+
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
    - `view`: Contains JavaFX FXML files for the UI.
- `src/main/resources`: Contains resource files (e.g., FXML files).

### Classes
- `com.example.votingapp.DAO`: Contains interfaces for DAOs (Data Access Objects).
- `com.example.votingapp.DAOImplementation`: Contains implementations for DAO interfaces.
- `com.example.votingapp.model`: Contains model classes representing entities.
- `com.example.votingapp.view`: Contains FXML files for the UI views.

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
- Use the provided SQL scripts to create tables and insert initial data.

## Contributors
- [EL HADDATI Mohamed](link-to-your-profile) - Creator
- [Boutbib Ayoub](link-to-your-profile) - Creator

## License
This project is licensed under the [MIT License](LICENSE).





