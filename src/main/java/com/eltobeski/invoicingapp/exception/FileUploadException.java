package com.eltobeski.invoicingapp.exception;

public class FileUploadException extends Exception {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable th) {
        super(message, th);
    }
}
