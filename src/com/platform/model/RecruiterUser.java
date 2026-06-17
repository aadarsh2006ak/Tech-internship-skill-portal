package com.platform.model;

import com.platform.service.JobPortalSystem;
import java.util.Scanner;

/**
 * Demonstrates INHERITANCE and POLYMORPHISM.
 * Inherits credentials from User, adding recruiter-specific properties
 * and overriding login() to view application records and manage candidates.
 */
public class RecruiterUser extends User {

    public RecruiterUser(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRoleDescription() {
        return "Recruiter - reviews candidate tech profiles and modifies applicant status.";
    }

    @Override
    public void login(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n==================================================");
        System.out.println(" Welcome back, Recruiter: " + getFullName());
        System.out.println("==================================================");

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Recruiter Control Panel ---");
            System.out.println("1. View Candidate Applications");
            System.out.println("2. Update Application Status");
            System.out.println("3. View Job Portal Metrics");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    portal.displayAllApplications();
                    break;
                case "2":
                    updateApplicationStatus(scanner, portal);
                    break;
                case "3":
                    portal.displayMetrics();
                    break;
                case "4":
                    System.out.println("Logging out from Recruiter Portal...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void updateApplicationStatus(Scanner scanner, JobPortalSystem portal) {
        System.out.println("\n--- Update Application Status ---");
        portal.displayAllApplications();
        
        System.out.print("Enter the Application ID to update (or 0 to cancel): ");
        try {
            int appId = Integer.parseInt(scanner.nextLine().trim());
            if (appId == 0) {
                return;
            }
            
            System.out.println("Choose new status:");
            System.out.println("1. SHORTLISTED");
            System.out.println("2. REJECTED");
            System.out.println("3. UNDER_REVIEW");
            System.out.print("Select status (1-3): ");
            int statusChoice = Integer.parseInt(scanner.nextLine().trim());
            
            String newStatus;
            switch (statusChoice) {
                case 1:
                    newStatus = "SHORTLISTED";
                    break;
                case 2:
                    newStatus = "REJECTED";
                    break;
                case 3:
                    newStatus = "UNDER_REVIEW";
                    break;
                default:
                    System.out.println("Invalid choice. Status update aborted.");
                    return;
            }
            
            portal.updateApplicationStatus(appId, newStatus);
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        }
    }
}
