package com.platform.test;

import com.platform.model.User;
import com.platform.model.StudentUser;
import com.platform.model.RecruiterUser;
import com.platform.model.StudentProfile;
import com.platform.service.ApplicationProcess;
import com.platform.service.JobPortalSystem;

public class TestRunner {
    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        runAllTests();
    }

    public static void runAllTests() {
        testsRun = 0;
        testsPassed = 0;
        
        System.out.println("\n==============================================");
        System.out.println("   RUNNING AUTOMATED OOP VERIFICATION TESTS   ");
        System.out.println("==============================================");

        testEncapsulation();
        testInheritance();
        testPolymorphism();
        testAbstraction();

        System.out.println("\n==============================================");
        System.out.println(" TEST RESULTS: " + testsPassed + "/" + testsRun + " passed.");
        System.out.println("==============================================");
    }

    private static void assertEquals(Object expected, Object actual, String testName) {
        testsRun++;
        if (expected == null && actual == null) {
            testsPassed++;
            System.out.println(" [PASS] " + testName);
        } else if (expected != null && expected.equals(actual)) {
            testsPassed++;
            System.out.println(" [PASS] " + testName);
        } else {
            System.out.println(" [FAIL] " + testName + " | Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String testName) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println(" [PASS] " + testName);
        } else {
            System.out.println(" [FAIL] " + testName + " | Condition was false.");
        }
    }

    private static void testEncapsulation() {
        System.out.println("\n--- Testing Encapsulation ---");
        
        // 1. Check valid initialization
        try {
            StudentProfile profile = new StudentProfile(3.8, "Resume details", "HTML, CSS");
            assertEquals(3.8, profile.getGpa(), "Valid GPA retrieval");
            assertEquals("Resume details", profile.getResumeContent(), "Valid Resume retrieval");
        } catch (Exception e) {
            assertTrue(false, "Valid initialization failed unexpectedly: " + e.getMessage());
        }

        // 2. Check GPA out of bounds validation (high)
        try {
            new StudentProfile(4.5, "Resume details", "HTML, CSS");
            assertTrue(false, "GPA > 4.0 should have failed");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "GPA > 4.0 successfully rejected");
        }

        // 3. Check GPA out of bounds validation (negative)
        try {
            new StudentProfile(-0.1, "Resume details", "HTML, CSS");
            assertTrue(false, "GPA < 0.0 should have failed");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "GPA < 0.0 successfully rejected");
        }

        // 4. Check empty resume validation
        try {
            new StudentProfile(3.0, "   ", "HTML, CSS");
            assertTrue(false, "Empty resume content should have failed");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Empty resume successfully rejected");
        }
    }

    private static void testInheritance() {
        System.out.println("\n--- Testing Inheritance ---");

        StudentProfile profile = new StudentProfile(3.5, "Resume summary", "JS, React");
        StudentUser student = new StudentUser("test_student", "pass", "Test Student", profile);
        RecruiterUser recruiter = new RecruiterUser("test_recruiter", "pass", "Test Recruiter");

        // Verify base class inheritance
        assertTrue(student instanceof User, "StudentUser inherits from User");
        assertTrue(recruiter instanceof User, "RecruiterUser inherits from User");

        // Verify common functionality inherited from User
        assertEquals("Test Student", student.getFullName(), "StudentUser inherits getFullName()");
        assertEquals("Test Recruiter", recruiter.getFullName(), "RecruiterUser inherits getFullName()");
        assertTrue(student.authenticate("pass"), "StudentUser inherits authenticate()");
    }

    private static void testPolymorphism() {
        System.out.println("\n--- Testing Polymorphism ---");

        StudentProfile profile = new StudentProfile(3.5, "Resume summary", "JS, React");
        User student = new StudentUser("test_student", "pass", "Test Student", profile);
        User recruiter = new RecruiterUser("test_recruiter", "pass", "Test Recruiter");

        // Polmorphic array of User references
        User[] users = { student, recruiter };

        // Even though references are typed as 'User', JVM routes calls dynamically
        assertEquals("Student - displays active software engineering roles and handles applications.", 
                     users[0].getRoleDescription(), 
                     "Polymorphic call resolves to StudentUser's description");

        assertEquals("Recruiter - reviews candidate tech profiles and modifies applicant status.", 
                     users[1].getRoleDescription(), 
                     "Polymorphic call resolves to RecruiterUser's description");
    }

    private static void testAbstraction() {
        System.out.println("\n--- Testing Abstraction ---");

        JobPortalSystem portal = new JobPortalSystem();
        StudentProfile profile = new StudentProfile(3.7, "Resume Project Info", "React, JavaScript");
        StudentUser testStudent = new StudentUser("tester", "p", "Test Student", profile);
        portal.registerStudent(testStudent);

        // Interact using the abstract class reference (Abstraction)
        ApplicationProcess process = portal.getApplicationProcessor();
        
        int initialCount = portal.getApplications().size();
        
        // Execute call using simple submission interface
        process.submitApplication(testStudent, "Backend Developer Intern");

        // Verify that the record was correctly saved internally by the concrete implementation
        int newCount = portal.getApplications().size();
        assertEquals(initialCount + 1, newCount, "Application record stored successfully in mock DB");
        
        JobPortalSystem.ApplicationRecord lastRecord = portal.getApplications().get(newCount - 1);
        assertEquals("Backend Developer Intern", lastRecord.getJobTitle(), "Correct job title stored");
        assertEquals("tester", lastRecord.getStudent().getUsername(), "Correct student linked");
        assertEquals("APPLIED", lastRecord.getStatus(), "Initial status is APPLIED");
    }
}
