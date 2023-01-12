package org.example.looam.order.inbound.rest;

import java.io.File;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import org.example.looam.order.inbound.MvcTest;

@WebMvcTest
public class BaseControllerTest {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;

  private MvcTest mvcTest;

  @BeforeEach
  void setUp() {
    mvcTest = new MvcTest(objectMapper, mockMvc);
  }

  protected ResultActions perform(MockHttpServletRequestBuilder builder) {
    return mvcTest.perform(builder);
  }

  protected ResultActions perform(MockHttpServletRequestBuilder builder, Object content) {
    return mvcTest.perform(builder, content);
  }

  protected ResultActions perform(
      MockHttpServletRequestBuilder builder, Object content, MediaType type) {
    return mvcTest.perform(builder, content, type);
  }

  protected ResultActions performFile(MockMultipartHttpServletRequestBuilder builder, File file) {
    return mvcTest.performFile(builder, file);
  }

  public <T> T parseResponse(ResultActions resultActions, Class<T> type) {
    return mvcTest.parseResponse(resultActions, type);
  }

  public <T> T parseResponse(ResultActions resultActions, TypeReference<T> typeReference) {
    return mvcTest.parseResponse(resultActions, typeReference);
  }
}
