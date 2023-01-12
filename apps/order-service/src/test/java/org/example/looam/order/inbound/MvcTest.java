package org.example.looam.order.inbound;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@AllArgsConstructor
public class MvcTest {
  protected ObjectMapper objectMapper;
  protected MockMvc mockMvc;

  @SneakyThrows
  public ResultActions perform(MockHttpServletRequestBuilder builder) {
    return mockMvc.perform(builder);
  }

  @SneakyThrows
  public ResultActions perform(MockHttpServletRequestBuilder builder, Object content) {
    return perform(builder, content, APPLICATION_JSON);
  }

  @SneakyThrows
  public ResultActions perform(
      MockHttpServletRequestBuilder builder, Object content, MediaType type) {
    return mockMvc.perform(
        builder.content(objectMapper.writeValueAsString(content)).contentType(type));
  }

  @SneakyThrows
  public ResultActions performFile(MockMultipartHttpServletRequestBuilder builder, File file) {
    return mockMvc.perform(
        builder
            .file(new MockMultipartFile("file", new FileInputStream(file)))
            .contentType(MULTIPART_FORM_DATA));
  }

  @SneakyThrows
  public <T> T parseResponse(ResultActions resultActions, Class<T> type) {
    String contentAsString =
        resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    return objectMapper.readValue(contentAsString, type);
  }

  @SneakyThrows
  public <T> T parseResponse(ResultActions resultActions, TypeReference<T> typeReference) {
    String contentAsString =
        resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    return objectMapper.readValue(contentAsString, typeReference);
  }
}
