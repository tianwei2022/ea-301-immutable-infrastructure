package org.example.looam.book.exception;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.example.looam.common.exception.AppException;
import org.example.looam.common.exception.DataNotFoundException;
import org.example.looam.common.exception.ErrorCode;
import org.example.looam.common.exception.ErrorKeyValue;
import org.example.looam.common.exception.ErrorResponse;
import org.example.looam.common.exception.ExceptionUtil;
import org.example.looam.common.exception.ForbiddenException;
import org.example.looam.common.exception.InvalidParameterException;
import org.example.looam.common.exception.UnauthorizedException;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ForbiddenException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ErrorResponse handleForbiddenException(ForbiddenException exception) {
    return new ErrorResponse(exception);
  }

  @ExceptionHandler({UnauthorizedException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ErrorResponse handleUnauthorizedException(UnauthorizedException exception) {
    return new ErrorResponse(exception);
  }

  @ExceptionHandler({DataNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleDataNotFoundException(DataNotFoundException exception) {
    return new ErrorResponse(exception);
  }

  @ExceptionHandler({AppException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleAppException(AppException exception) {
    return new ErrorResponse(exception);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleAppException(ConstraintViolationException exception) {
    List<ErrorKeyValue<String, String>> errors =
        exception.getConstraintViolations().stream()
            .map(
                validation ->
                    new ErrorKeyValue<>(
                        validation.getPropertyPath().toString(), validation.getMessage()))
            .collect(toList());
    return new ErrorResponse(new InvalidParameterException(errors));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorResponse runtimeException(RuntimeException e, HttpServletRequest request) {
    ExceptionUtil.printClientRequest(request);
    log.error(e.getMessage(), e);
    return new ErrorResponse(new AppException(ErrorCode.SERVER_ERROR, e.getMessage()));
  }
}
