package org.example.looam.common.exception;

public class DataNotFoundException extends AppException {
  private static final String ERROR_MSG = "数据未找到";
  private static final ErrorCode ERROR_CODE = ErrorCode.DATA_NOT_FOUND;

  public DataNotFoundException() {
    super(ERROR_CODE, ERROR_MSG);
  }

  public DataNotFoundException(String errorMsg) {
    super(ERROR_CODE, errorMsg);
  }

  public DataNotFoundException(String errorMsg, Object data) {
    super(ERROR_CODE, errorMsg, data);
  }

  public DataNotFoundException( Object data) {
    super(ERROR_CODE, data);
  }
}
