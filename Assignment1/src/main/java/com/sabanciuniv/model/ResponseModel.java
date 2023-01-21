package com.sabanciuniv.model;

public class ResponseModel<T> {
	
	private String message;
	
	private T data;
	
	public ResponseModel() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
