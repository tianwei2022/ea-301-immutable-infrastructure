package org.example.looam.common.exception;

public class InvalidParameterException extends AppException{

  private static final String ERROR_MSG = "非法的参数";
  private static final ErrorCode ERROR_CODE = ErrorCode.INVALID_PARAMETER;

  public InvalidParameterException() {
    super(ERROR_CODE, ERROR_MSG);
  }

  public InvalidParameterException(String errorMsg) {
    super(ERROR_CODE, errorMsg);
  }

  public InvalidParameterException(String errorMsg, Object data) {
    super(ERROR_CODE, errorMsg, data);
  }

  public InvalidParameterException( Object data) {
    super(ERROR_CODE, data);
  }
}
