package com.driver;

public class ApproverDoesNotHaveRights extends Exception {
    public ApproverDoesNotHaveRights() {
        super(" Approver does not have rights");
    }
}
