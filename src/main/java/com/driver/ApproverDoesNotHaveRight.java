package com.driver;

public class ApproverDoesNotHaveRight extends Exception {
    public ApproverDoesNotHaveRight() {
        super("Approver does not have rights");
    }
}
