package com.platform.service;

import com.platform.model.User;
import com.platform.model.StudentUser;
import com.platform.model.RecruiterUser;
import com.platform.model.StudentProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates simulated storage, system data lookup, and status tracking.
 * It contains the concrete implementation of the ApplicationProcess abstraction.
 */
public class JobPortalSystem {
    private List<User> users = new ArrayList<>();
    private List<String> jobs = new ArrayList<>();
    private List<ApplicationRecord> applications = new ArrayList<>();
    
    // Abstract application process instance
    private final ApplicationProcess appProcessor = new InMemoryApplicationProcess();

    public JobPortalSystem() {
        seedInitialData();
    }

    /**
     * Entity representing a recorded application.
     */
    public static class ApplicationRecord {
        private static int counter = 1001;
        private int id;
        private StudentUser student;
        private String jobTitle;
        private String status; // APPLIED, UNDER_REVIEW, SHORTLISTED, REJECTED

        public ApplicationRecord(StudentUser student, String jobTitle) {
            this.id = counter++;
            this.student = student;
            this.jobTitle = jobTitle;
            this.status = "APPLIED";
        }

        public int getId() { return id; }
        public StudentUser getStudent() { return student; }
        public String getJobTitle() { return jobTitle; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    /**
     * Concrete implementation of the Abstraction.
     * Hides the inner data storage mechanics from external classes.
     */
    private class InMemoryApplicationProcess extends ApplicationProcess {
        @Override
        public void submitApplication(StudentUser student, String jobTitle) {
            if (student == null || student.getProfile() == null) {
                throw new IllegalArgumentException("Cannot submit application: profile is missing.");
            }
            // Add a new record in our internal list
            ApplicationRecord record = new ApplicationRecord(student, jobTitle);
            applications.add(record);
            System.out.println("\n[Abstraction] Application submitted through ApplicationProcess interface!");
            System.out.println("Details: " + student.getFullName() + " applied for " + jobTitle + " (App ID: " + record.getId() + ")");
        }
    }

    public ApplicationProcess getApplicationProcessor() {
        return appProcessor;
    }

    private void seedInitialData() {
        // Pre-configured jobs
        jobs.add("Software Engineer Intern - React/JavaScript Focus");
        jobs.add("Frontend Developer - HTML, CSS & Vanilla JS");
        jobs.add("Junior Full-Stack Developer - Java & React");
        jobs.add("UI Engineer - Web Optimization (CSS, HTML)");

        // Pre-configured users
        StudentProfile profileAlice = new StudentProfile(3.9, "Portfolio: alice.dev. Worked on React dashboard projects.", "HTML, CSS, JavaScript, React");
        StudentUser alice = new StudentUser("alice", "alice123", "Alice Smith", profileAlice);
        
        StudentProfile profileBob = new StudentProfile(3.5, "GitHub: bobdev. Created landing pages and animations.", "HTML, CSS, JavaScript");
        StudentUser bob = new StudentUser("bob", "bob123", "Bob Jones", profileBob);

        RecruiterUser recruiter = new RecruiterUser("recruiter", "recruiter123", "Sarah Connor (Google HR)");

        users.add(alice);
        users.add(bob);
        users.add(recruiter);

        // Seed some initial applications
        appProcessor.submitApplication(alice, jobs.get(0)); // Alice applied to SWE Intern
        appProcessor.submitApplication(bob, jobs.get(1));   // Bob applied to Frontend Dev
    }

    public User loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                if (u.authenticate(password)) {
                    return u;
                } else {
                    return null; // Username matches, incorrect password
                }
            }
        }
        return null; // User not found
    }

    public void displayJobs() {
        System.out.println("\n=== Available Software Engineering Roles ===");
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println((i + 1) + ". " + jobs.get(i));
        }
    }

    public void applyForJob(StudentUser student, int jobNum) {
        if (jobNum < 1 || jobNum > jobs.size()) {
            System.out.println("Error: Invalid job selection number.");
            return;
        }
        String selectedJob = jobs.get(jobNum - 1);

        // Check if student already applied for this job
        for (ApplicationRecord record : applications) {
            if (record.getStudent().getUsername().equalsIgnoreCase(student.getUsername())
                    && record.getJobTitle().equals(selectedJob)) {
                System.out.println("Error: You have already applied for this job position.");
                return;
            }
        }

        // Apply via Abstraction
        appProcessor.submitApplication(student, selectedJob);
    }

    public void displayApplicationsForStudent(StudentUser student) {
        boolean found = false;
        for (ApplicationRecord record : applications) {
            if (record.getStudent().getUsername().equalsIgnoreCase(student.getUsername())) {
                System.out.println("- [" + record.getId() + "] " + record.getJobTitle() + " | Status: " + record.getStatus());
                found = true;
            }
        }
        if (!found) {
            System.out.println("You have not applied to any jobs yet.");
        }
    }

    public void displayAllApplications() {
        System.out.println("\n=== Received Candidate Applications ===");
        if (applications.isEmpty()) {
            System.out.println("No active applications found.");
            return;
        }

        for (ApplicationRecord record : applications) {
            StudentUser student = record.getStudent();
            StudentProfile profile = student.getProfile();
            System.out.println("Application ID: " + record.getId());
            System.out.println("Job Applied For: " + record.getJobTitle());
            System.out.println("Candidate Name: " + student.getFullName());
            System.out.println("Candidate Profile: " + (profile != null ? profile.toString() : "No Profile Setup"));
            System.out.println("Current Status: " + record.getStatus());
            System.out.println("----------------------------------------");
        }
    }

    public void updateApplicationStatus(int appId, String status) {
        for (ApplicationRecord record : applications) {
            if (record.getId() == appId) {
                record.setStatus(status);
                System.out.println("Application ID " + appId + " status updated to: " + status);
                return;
            }
        }
        System.out.println("Error: Application ID " + appId + " not found.");
    }

    public void displayMetrics() {
        System.out.println("\n=== Job Portal Metrics (Recruiter Overview) ===");
        System.out.println("Total Applications Received: " + applications.size());
        
        int shortlistedCount = 0;
        int rejectedCount = 0;
        int underReviewCount = 0;
        int appliedCount = 0;
        double sumGpa = 0.0;
        int gpaCount = 0;

        for (ApplicationRecord record : applications) {
            switch (record.getStatus()) {
                case "SHORTLISTED": shortlistedCount++; break;
                case "REJECTED": rejectedCount++; break;
                case "UNDER_REVIEW": underReviewCount++; break;
                default: appliedCount++; break;
            }
            if (record.getStudent().getProfile() != null) {
                sumGpa += record.getStudent().getProfile().getGpa();
                gpaCount++;
            }
        }

        System.out.println("Applied/Pending: " + appliedCount);
        System.out.println("Under Review: " + underReviewCount);
        System.out.println("Shortlisted: " + shortlistedCount);
        System.out.println("Rejected: " + rejectedCount);
        if (gpaCount > 0) {
            System.out.printf("Average Applicant GPA: %.2f / 4.00\n", (sumGpa / gpaCount));
        } else {
            System.out.println("Average Applicant GPA: N/A");
        }
    }

    // Helper method for unit tests
    public List<ApplicationRecord> getApplications() {
        return applications;
    }
    
    // Helper method to add new users for testing
    public void registerStudent(StudentUser student) {
        users.add(student);
    }
}
