package org.example.looam.common.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppException extends RuntimeException {

  protected ErrorCode errorCode;

  protected Object data;

  public AppException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  public AppException(ErrorCode code, String errorMsg) {
    super(errorMsg);
    this.errorCode = code;
  }

  public AppException(ErrorCode code, String errorMsg, Object data) {
    super(errorMsg);
    this.errorCode = code;
    this.data = data;
  }

  public AppException(ErrorCode errorCode, Object data) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.data = data;
  }
}
