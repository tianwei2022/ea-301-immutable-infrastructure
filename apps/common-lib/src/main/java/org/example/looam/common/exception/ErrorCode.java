package org.example.looam.common.exception;

import lombok.Getter;

@Getter
public class ErrorCode {

  public static final ErrorCode SERVER_ERROR = new ErrorCode("server_error");

  public static final ErrorCode CLIENT_ERROR = new ErrorCode("client_error");

  public static final ErrorCode FORBIDDEN = new ErrorCode("forbidden");

  public static final ErrorCode UNAUTHORIZED = new ErrorCode("unauthorized");

  public static final ErrorCode INVALID_PARAMETER = new ErrorCode("invalid_parameter");

  public static final ErrorCode DATA_NOT_FOUND = new ErrorCode("data_not_found");

  protected String code;

  @Deprecated
  protected String desc;

  public ErrorCode(String errorCode) {
    this.code = errorCode;
  }

  @Deprecated
  protected ErrorCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
