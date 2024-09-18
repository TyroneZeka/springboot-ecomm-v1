package org.mufasadev.ecommerce.project.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String field;
    private String fieldName;
    private Long fieldId;

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s with %s: %s Not Found.", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }
    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s with %s : %d Not Found.", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
