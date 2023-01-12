package org.example.looam.common.exception;

import feign.FeignException;
import feign.Request;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class ExceptionUtil {

  public static void printClientRequest(HttpServletRequest request) {

    String queryString = request.getQueryString();
    String method = request.getMethod();
    String url = request.getRequestURL().toString();

    String contentString = null;
    if (request instanceof ContentCachingRequestWrapper) {
      byte[] contentAsByteArray = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
      contentString = new String(contentAsByteArray, 0, contentAsByteArray.length);
    }

    log.error("[RuntimeException][request] {} {}  body:{}", method,
        url + "?" + queryString, contentString);
  }

  public static  void printFeignResponse(FeignException e) {
    log.error("[FeignException][response] status code: {}, headers: {}, body: {}", e.status(),
        e.responseHeaders(),
        e.contentUTF8());
  }

  public static  void printFeignRequest(FeignException e) {
    Request feignRequest = e.request();
    if (feignRequest == null) {
      return;
    }
    byte[] body = feignRequest.body();
    if (body == null) {
      body = new byte[0];
    }
    String bodyStr = new String(body, 0, body.length);
    String method = feignRequest.httpMethod().name();
    String url = feignRequest.url();
    Map<String, Collection<String>> headers = feignRequest.headers();
    log.error("[FeignException][request]: {} {}, header: {}, body: {}", method, url,
        headers, bodyStr);
  }
}
