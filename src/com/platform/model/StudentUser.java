package com.platform.model;

import com.platform.service.JobPortalSystem;
import java.util.Scanner;

/**
 * Demonstrates INHERITANCE and POLYMORPHISM.
 * Inherits credentials from User, adding student-specific properties (StudentProfile)
 * and overriding login() to show student-specific job search actions.
 */
public class StudentUser extends User {
    private StudentProfile profile;

    public StudentUser(String username, String password, String fullName, StudentProfile profile) {
        super(username, password, fullName);
        this.profile = profile;
    }

    public StudentProfile getProfile() {
        return profile;
    }

    @Override
    public String getRoleDescription() {
        return "Student - displays active software engineering roles and handles applications.";
    }

    public void setProfile(StudentProfile profile) {
        this.profile = profile;
    }

    @Override
    public void login(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n==================================================");
        System.out.println(" Welcome back, Student: " + getFullName());
        System.out.println("==================================================");

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Student Control Panel ---");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile Tech Stack & Grades");
            System.out.println("3. Browse Available Jobs & Apply");
            System.out.println("4. View Applied Jobs & Statuses");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    updateProfile(scanner);
                    break;
                case "3":
                    browseAndApply(scanner, portal);
                    break;
                case "4":
                    viewAppliedJobs(portal);
                    break;
                case "5":
                    System.out.println("Logging out from Student Portal...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("\n--- Current Profile ---");
        if (profile == null) {
            System.out.println("No profile created yet!");
        } else {
            System.out.println(profile);
        }
    }

    private void updateProfile(Scanner scanner) {
        System.out.println("\n--- Update Profile ---");
        try {
            System.out.print("Enter your GPA (0.0 to 4.0): ");
            double gpa = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter your Resume Summary / Projects: ");
            String resume = scanner.nextLine().trim();

            System.out.print("Enter your Tech Stack (e.g. HTML, CSS, JavaScript, React): ");
            String tech = scanner.nextLine().trim();

            // Setters will validate input and enforce encapsulation constraints
            if (profile == null) {
                profile = new StudentProfile(gpa, resume, tech);
            } else {
                profile.setGpa(gpa);
                profile.setResumeContent(resume);
                profile.setTechStack(tech);
            }
            System.out.println("Profile updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: GPA must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private void browseAndApply(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n--- Browse & Apply ---");
        portal.displayJobs();
        System.out.print("\nEnter the number of the job you want to apply for (or 0 to cancel): ");
        try {
            int jobNum = Integer.parseInt(scanner.nextLine().trim());
            if (jobNum == 0) {
                return;
            }
            portal.applyForJob(this, jobNum);
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        }
    }

    private void viewAppliedJobs(JobPortalSystem portal) {
        System.out.println("\n--- Applied Jobs ---");
        portal.displayApplicationsForStudent(this);
    }
}
