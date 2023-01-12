package org.example.looam.common.exception;

public class ForbiddenException extends AppException{

  private static final String ERROR_MSG = "禁止访问";
  private static final ErrorCode ERROR_CODE = ErrorCode.FORBIDDEN;

  public ForbiddenException() {
    super(ERROR_CODE, ERROR_MSG);
  }

  public ForbiddenException(String errorMsg) {
    super(ERROR_CODE, errorMsg);
  }

  public ForbiddenException(String errorMsg, Object data) {
    super(ERROR_CODE, errorMsg, data);
  }

  public ForbiddenException( Object data) {
    super(ERROR_CODE, data);
  }
}
