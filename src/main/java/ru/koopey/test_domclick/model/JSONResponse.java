package ru.koopey.test_domclick.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSONResponse {
    private Boolean success;
    private String message;
}