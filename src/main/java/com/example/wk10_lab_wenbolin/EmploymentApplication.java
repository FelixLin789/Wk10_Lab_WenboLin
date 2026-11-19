package com.example.wk10_lab_wenbolin;

import java.time.LocalDate;


public class EmploymentApplication {
    private int id;
    private String fullName;
    private String currentAddress;
    private String contactNumber;
    private String emailAddress;
    private String highestEducation;
    private String gender;
    private LocalDate dateAvailable;
    private String desiredPosition;
    private double desiredSalary;
    private String legallyAuthorized;
    private String hasRelatives;
    private String relativesExplanation;
    private String applicantSignature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(LocalDate dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public double getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(double desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public String getLegallyAuthorized() {
        return legallyAuthorized;
    }

    public void setLegallyAuthorized(String legallyAuthorized) {
        this.legallyAuthorized = legallyAuthorized;
    }

    public String getHasRelatives() {
        return hasRelatives;
    }

    public void setHasRelatives(String hasRelatives) {
        this.hasRelatives = hasRelatives;
    }

    public String getRelativesExplanation() {
        return relativesExplanation;
    }

    public void setRelativesExplanation(String relativesExplanation) {
        this.relativesExplanation = relativesExplanation;
    }

    public String getApplicantSignature() {
        return applicantSignature;
    }

    public void setApplicantSignature(String applicantSignature) {
        this.applicantSignature = applicantSignature;
    }
}
