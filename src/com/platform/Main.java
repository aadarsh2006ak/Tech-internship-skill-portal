package com.platform;

import com.platform.model.User;
import com.platform.model.StudentUser;
import com.platform.model.StudentProfile;
import com.platform.service.JobPortalSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JobPortalSystem portal = new JobPortalSystem();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println("   STUDENT TECH PORTAL & RECRUITMENT SYSTEMS");
        System.out.println("      Implementing Core Java OOP Principles");
        System.out.println("==================================================");
        System.out.println("OOP Features Illustrated:");
        System.out.println(" - [Encapsulation] StudentProfile validation checks");
        System.out.println(" - [Inheritance] User base class extended by Student/Recruiter");
        System.out.println(" - [Polymorphism] Polymorphic User.login() dynamic routing");
        System.out.println(" - [Abstraction] Hiding storage mechanics using ApplicationProcess");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n=== Main Job Portal Menu ===");
            System.out.println("1. Log In");
            System.out.println("2. Register New Student Account");
            System.out.println("3. Run Automated OOP Verification Tests");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleLogin(scanner, portal);
                    break;
                case "2":
                    handleRegistration(scanner, portal);
                    break;
                case "3":
                    runAutomatedTests();
                    break;
                case "4":
                    System.out.println("Exiting the application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void handleLogin(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n--- User Login ---");
        System.out.println("Demo Accounts Available:");
        System.out.println(" * Student: username = 'alice', password = 'alice123'");
        System.out.println(" * Student: username = 'bob', password = 'bob123'");
        System.out.println(" * Recruiter: username = 'recruiter', password = 'recruiter123'");
        System.out.println("-------------------------");
        
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        User loggedInUser = portal.loginUser(username, password);

        if (loggedInUser != null) {
            // POLYMORPHISM IN ACTION:
            // The method logic is resolved at runtime. Depending on whether
            // loggedInUser refers to a StudentUser or a RecruiterUser,
            // the respective subclass menu options are displayed.
            System.out.println("\n[Polymorphism] Invoking polymorphic login() method on User reference.");
            loggedInUser.login(scanner, portal);
        } else {
            System.out.println("\nError: Login failed! Invalid username or password.");
        }
    }

    private static void handleRegistration(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n--- Student Registration ---");
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            System.out.println("Error: Fields cannot be empty!");
            return;
        }

        System.out.println("Let's configure your profile variables (Demonstrating Encapsulation):");
        try {
            System.out.print("Enter your GPA (0.0 to 4.0): ");
            double gpa = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.print("Enter your Resume Projects/Work Summary: ");
            String resume = scanner.nextLine().trim();
            
            System.out.print("Enter your tech stack (comma-separated, e.g. HTML, React): ");
            String techStack = scanner.nextLine().trim();

            // Encapsulation validation will run here
            StudentProfile profile = new StudentProfile(gpa, resume, techStack);
            StudentUser student = new StudentUser(username, password, fullName, profile);
            
            portal.registerStudent(student);
            System.out.println("\nSuccess: Account created! You can now log in using credentials: " + username);
        } catch (NumberFormatException e) {
            System.out.println("Error: GPA must be a valid decimal number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration Validation Error: " + e.getMessage());
        }
    }

    private static void runAutomatedTests() {
        System.out.println("\nInvoking test suite directly...");
        // Run tests inside TestRunner
        com.platform.test.TestRunner.runAllTests();
    }
}
