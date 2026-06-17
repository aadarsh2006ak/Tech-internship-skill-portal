package com.platform.model;

/**
 * Demonstrates ENCAPSULATION.
 * Fields are private, and access is controlled via public getter/setter methods
 * which enforce business validation rules (data integrity).
 */
public class StudentProfile {
    private double gpa; // Encapsulated GPA (0.0 to 4.0 scale)
    private String resumeContent; // Encapsulated Resume summary/description
    private String techStack; // Encapsulated Tech Stack details (e.g., HTML, CSS, JavaScript, React)

    public StudentProfile(double gpa, String resumeContent, String techStack) {
        setGpa(gpa);
        setResumeContent(resumeContent);
        setTechStack(techStack);
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
        this.gpa = gpa;
    }

    public String getResumeContent() {
        return resumeContent;
    }

    public void setResumeContent(String resumeContent) {
        if (resumeContent == null || resumeContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Resume content cannot be empty.");
        }
        this.resumeContent = resumeContent;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        if (techStack == null || techStack.trim().isEmpty()) {
            throw new IllegalArgumentException("Tech stack cannot be empty.");
        }
        this.techStack = techStack;
    }

    @Override
    public String toString() {
        return "GPA: " + gpa + " | Tech Stack: [" + techStack + "] | Resume: " + resumeContent;
    }
}
