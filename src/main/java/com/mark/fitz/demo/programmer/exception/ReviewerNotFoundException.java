package com.mark.fitz.demo.programmer.exception;

public class ReviewerNotFoundException extends RuntimeException {

    public ReviewerNotFoundException() {
        super("Reviewer not found");
    }

}
