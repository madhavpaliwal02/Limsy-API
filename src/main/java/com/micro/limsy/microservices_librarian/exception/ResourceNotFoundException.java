package com.micro.limsy.microservices_librarian.exception;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is not found with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
