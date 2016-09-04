package com.android.decidir.sdk.dto;


import java.io.Serializable;

/**
 * Created by ezequiel on 2/6/16.
 */
public class DecidirResponse<T> implements Serializable{

  private int status;
  private T result;
  private String message;


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
