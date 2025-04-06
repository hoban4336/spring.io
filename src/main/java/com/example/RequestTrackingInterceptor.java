// package com.example;

// import co.elastic.apm.api.ElasticApm;
// import co.elastic.apm.api.Transaction;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.stream.Collectors;

// @Component
// public class RequestTrackingInterceptor implements HandlerInterceptor {
//     private static final Logger logger = LoggerFactory.getLogger(RequestTrackingInterceptor.class);

//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         Transaction transaction = ElasticApm.startTransaction();
//         transaction.setName(request.getMethod() + " " + request.getRequestURI());
        
//         // Request body 읽기
//         String requestBody = "";
//         if (request.getContentLength() > 0) {
//             try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
//                 requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
//             }
//         }
        
//         transaction.addTag("request_body", requestBody);
//         request.setAttribute("transaction", transaction);
        
//         return true;
//     }

//     @Override
//     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//         Transaction transaction = (Transaction) request.getAttribute("transaction");
//         if (transaction != null) {
//             transaction.addTag("response_status", response.getStatus());
//             transaction.end();
            
//             // 첫 번째 요청 로깅
//             ApplicationLifecycleListener.logFirstRequest(
//                 request.getRequestURI(),
//                 "Status: " + response.getStatus()
//             );
//         }
//     }
// } 