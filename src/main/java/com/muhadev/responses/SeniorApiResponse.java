package com.muhadev.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorApiResponse<T> {
	private boolean success;
    private int status;
    private String message;
    private T data;

    public SeniorApiResponse(boolean success, int status, String message) {
    	this.success = success;
        this.status = status;
        this.message = message;
    }
}