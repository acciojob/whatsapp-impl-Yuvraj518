package com.driver;

public class GroupDoesNotExistException extends Exception {
    public GroupDoesNotExistException() {
        super("Group does not exist");
    }
}
