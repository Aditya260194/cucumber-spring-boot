package com.example.cucumber.bdds;

import com.example.cucumberspringboot.CucumberSpringBootApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {

    static ResponseResult latestResponse;

    @Autowired
    protected RestTemplate restTemplate;

    void executeGet(String url) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResult(response));
            }
        });
    }

    void executePost() throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
                .execute("http://localhost:8082/test", HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.hadError) {
                        return (errorHandler.getResults());
                    } else {
                        return (new ResponseResult(response));
                    }
                });
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResult results = null;
        private Boolean hadError = false;

        private ResponseResult getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResult(response);
        }
    }

}
