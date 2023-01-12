package org.example.looam.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
  private final String errorCode;
  private final String message;
  private final Object data;

  public ErrorResponse(AppException appException) {
    this.errorCode = appException.getErrorCode().getCode();
    this.message = appException.getMessage();
    this.data = appException.getData();
  }
}
