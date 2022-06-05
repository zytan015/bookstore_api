package com.bookstore.api.exception;

public class ResourceNotFoundException extends RuntimeException {
	  private static final long serialVersionUID = 1L;
	  public ResourceNotFoundException(String msg) {
	    super(msg);
	  }
	}