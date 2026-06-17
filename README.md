# Student Tech Portal & Recruitment System (Java OOP)

A console-based Java application that simulates a **student recruitment platform** where students can create profiles and apply for jobs, while recruiters can review applications and manage hiring workflows.

This project demonstrates the **four core Object-Oriented Programming (OOP) concepts** using a real-world recruitment system.

---

## Features

### Student Module

* Create and manage student profiles
* Add GPA, resume details, and tech stack
* Browse and apply for available jobs
* Track application status

### Recruiter Module

* View student applications
* Update application status
* Monitor recruitment statistics

---

## OOP Concepts Used

### 1. Encapsulation

* Student data is protected using private variables.
* Validation rules ensure only valid GPA and profile information are accepted.

Example:

```java
private double gpa;

public void setGpa(double gpa) {
    if(gpa >= 0 && gpa <= 4.0) {
        this.gpa = gpa;
    }
}
```

---

### 2. Inheritance

* Common user functionality is stored in the `User` base class.
* `StudentUser` and `RecruiterUser` inherit shared behavior.

Structure:

```text
User
├── StudentUser
└── RecruiterUser
```

---

### 3. Polymorphism

* Different login behavior based on user type.

Example:

```java
user.login(scanner, portal);
```

The correct dashboard opens automatically.

---

### 4. Abstraction

* Complex application management logic is hidden behind simple methods.

Example:

```java
submitApplication(student, jobTitle);
```

---

## Project Structure

```text
src/
└── com/platform/
    ├── Main.java
    ├── model/
    │   ├── User.java
    │   ├── StudentUser.java
    │   ├── RecruiterUser.java
    │   └── StudentProfile.java
    ├── service/
    │   ├── ApplicationProcess.java
    │   └── JobPortalSystem.java
    └── test/
        └── TestRunner.java
```

---

## Technologies Used

* Java
* Object-Oriented Programming (OOP)
* Collections Framework
* CLI (Console Application)

---

## How to Run

### Compile

```bash
javac -d bin src/com/platform/model/*.java src/com/platform/service/*.java src/com/platform/test/*.java src/com/platform/Main.java
```

### Run Application

```bash
java -cp bin com.platform.Main
```

### Run Tests

```bash
java -cp bin com.platform.test.TestRunner
```

---

## Demo Credentials

### Student

```text
Username: alice
Password: alice123
```

### Student

```text
Username: bob
Password: bob123
```

### Recruiter

```text
Username: recruiter
Password: recruiter123
```

---

## Future Upgrades

### Database Integration

* Store data permanently using MySQL or PostgreSQL
* Connect through JDBC

### Spring Boot REST API

* Convert backend into API services
* Enable frontend integration

### Web Dashboard

* Replace CLI with React-based UI

### Smart Candidate Search

* Filter students by GPA and tech stack
* Use Java Streams and Lambdas

### Resume Upload

* Support PDF and DOC resume parsing

### Authentication

* Add secure login and password encryption

### Admin Panel

* Manage users, jobs, and reports

---

## Learning Outcome

This project demonstrates:

* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Real-world Java application structure
* Scalable software design principles
