package com.driver;

public class NotAllowedToSendMessageException extends Throwable {
    public NotAllowedToSendMessageException() {
        super("You are not allowed to send message");
    }
}
