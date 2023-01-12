package org.example.looam.common.exception;

public class UnauthorizedException extends AppException{

  private static final String ERROR_MSG = "权限不足";
  private static final ErrorCode ERROR_CODE = ErrorCode.UNAUTHORIZED;

  public UnauthorizedException() {
    super(ERROR_CODE, ERROR_MSG);
  }

  public UnauthorizedException(String errorMsg) {
    super(ERROR_CODE, errorMsg);
  }

  public UnauthorizedException(String errorMsg, Object data) {
    super(ERROR_CODE, errorMsg, data);
  }

  public UnauthorizedException( Object data) {
    super(ERROR_CODE, data);
  }
}
