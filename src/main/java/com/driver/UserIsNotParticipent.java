package com.driver;

public class UserIsNotParticipent extends Exception {
    public UserIsNotParticipent() {
        super("User is not a participant");
    }
}
