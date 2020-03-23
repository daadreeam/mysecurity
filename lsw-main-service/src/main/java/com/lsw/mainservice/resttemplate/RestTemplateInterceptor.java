package com.lsw.mainservice.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;


/**
 * resttemplate拦截器
 * 参考 https://blog.csdn.net/jaywheel/article/details/79225561
 */
@Component
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        tranceRequest(httpRequest, body);

        HttpHeaders headers1 = httpRequest.getHeaders();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers1.set(name,request.getHeader(name));
        }
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, body);
        traceResponse(response);
        return response;

    }

    private void tranceRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
        log.info("======= request begin ========");

        log.info("uri : {}", request.getURI());
        log.info("method : {}", request.getMethod());
        log.info("headers : {}", request.getHeaders());
        log.info("request body : {}", new String(body, "UTF-8"));

        log.info("======= request end ========");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                inputStringBuilder.append(line);
//                inputStringBuilder.append('\n');
//                line = bufferedReader.readLine();
//            }
//        }
        log.info("============================response begin==========================================");
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", inputStringBuilder.toString());
        log.info("=======================response end=================================================");
    }
}
