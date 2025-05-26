# QUIZAPP-JAVA-PROJECT


>Description:
A desktop-based quiz application developed in Java that allows users to take quizzes. The application stores user names and their scores in a MySQL database using JDBC.

Purpose:
To demonstrate skills in Java programming, database integration, and building interactive applications.


>Features

User-friendly quiz interface

Score calculation

MySQL database integration

Stores username and score





>Tools & Technologies

Java (JDK 8 or above)

MySQL

JDBC (Java Database Connectivity)

IDE: IntelliJ IDEA/ VS code

MySQL Workbench 





>Project Structure

Main.java – entry point

DatabaseManager.java – handles database connection and operations

QuizLogic.java – contains quiz questions and logic

resources/ – folder for storing optional assets (like text files or configs)




>Database Design

Database: quiz_app

Table: user_scores


Table Fields: | Field     | Type         | |-----------|--------------| | id        | INT, AUTO_INCREMENT | | username  | VARCHAR(100) | | score     | INT          |





>How It Works

1. User enters name and starts quiz.


2. Quiz questions are shown.


3. Score is calculated at the end.


4. Username and score are saved in the database.






>How to Run the Project

1. Install Java and MySQL


2. Create the database and table:



CREATE DATABASE quiz_app;
USE quiz_app;
CREATE TABLE user_scores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    score INT
);

3. Import the JDBC jar file into your Java project.


4. Compile and run Main.java.


5. Follow the quiz prompts.










