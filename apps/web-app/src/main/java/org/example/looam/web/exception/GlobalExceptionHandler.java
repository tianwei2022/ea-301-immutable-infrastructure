package org.example.looam.web.exception;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.example.looam.common.exception.AppException;
import org.example.looam.common.exception.ErrorCode;
import org.example.looam.common.exception.ErrorResponse;
import org.example.looam.common.exception.ExceptionUtil;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @Autowired private ObjectMapper objectMapper;

  @ExceptionHandler({AppException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleAppException(AppException exception) {
    return new ErrorResponse(exception);
  }

  @SneakyThrows
  @ExceptionHandler({FeignException.class})
  public ResponseEntity<ErrorResponse> feignServerException(FeignException e) {
    if (e.status() >= 500) {
      ExceptionUtil.printFeignResponse(e);
      ExceptionUtil.printFeignRequest(e);
      return new ResponseEntity<>(
          new ErrorResponse(new AppException(ErrorCode.SERVER_ERROR, "服务器内部错误", null)),
          HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      HttpStatus status = HttpStatus.resolve(e.status());
      ErrorResponse errorResponse = objectMapper.readValue(e.getMessage(), ErrorResponse.class);
      return new ResponseEntity<>(
          errorResponse, Objects.nonNull(status) ? status : HttpStatus.BAD_REQUEST);
    }
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorResponse runtimeException(RuntimeException e, HttpServletRequest request) {
    ExceptionUtil.printClientRequest(request);
    log.error("[RuntimeException][exception] " + e.getMessage(), e);
    return new ErrorResponse(new AppException(ErrorCode.SERVER_ERROR, "服务器内部错误", null));
  }
}
