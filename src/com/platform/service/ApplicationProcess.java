package com.platform.service;

import com.platform.model.StudentUser;

/**
 * Demonstrates ABSTRACTION.
 * This class hides the complex implementation details of how applications
 * are saved, validated, or sent to a database/in-memory storage.
 * The client only interacts with the simple submitApplication() interface.
 */
public abstract class ApplicationProcess {
    
    /**
     * Submits an application for a student.
     * Concrete implementations determine the storage and tracking details.
     *
     * @param student  the student applying
     * @param jobTitle the title of the job
     */
    public abstract void submitApplication(StudentUser student, String jobTitle);
}
