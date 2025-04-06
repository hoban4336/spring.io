// package com.example;

// import co.elastic.apm.api.ElasticApm;
// import co.elastic.apm.api.Transaction;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextClosedEvent;
// import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.stereotype.Component;

// @Component
// public class ApplicationLifecycleListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationListener<ContextClosedEvent> {
//     private static final Logger logger = LoggerFactory.getLogger(ApplicationLifecycleListener.class);
//     private static boolean isFirstRequest = true;

//     @Override
//     public void onApplicationEvent(ContextRefreshedEvent event) {
//         Transaction transaction = ElasticApm.startTransaction();
//         transaction.setName("Application Startup");
        
//         try {
//             logger.info("Application started and ready to accept traffic");
//             transaction.addTag("event", "application_started");
//         } finally {
//             transaction.end();
//         }
//     }

//     @Override
//     public void onApplicationEvent(ContextClosedEvent event) {
//         Transaction transaction = ElasticApm.startTransaction();
//         transaction.setName("Application Shutdown");
        
//         try {
//             logger.info("Application is shutting down gracefully");
//             transaction.addTag("event", "application_shutdown");
            
//             // 종료 시점의 메모리 상태 로깅
//             Runtime runtime = Runtime.getRuntime();
//             long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
//             long totalMemory = runtime.totalMemory() / 1024 / 1024;
//             logger.info("Memory usage at shutdown - Used: {}MB, Total: {}MB", usedMemory, totalMemory);
//             transaction.addTag("memory_used_mb", usedMemory);
//             transaction.addTag("memory_total_mb", totalMemory);
//         } finally {
//             transaction.end();
//         }
//     }

//     public static void logFirstRequest(String requestPath, String responseBody) {
//         if (isFirstRequest) {
//             Transaction transaction = ElasticApm.startTransaction();
//             transaction.setName("First Request");
            
//             try {
//                 logger.info("First request received - Path: {}, Response: {}", requestPath, responseBody);
//                 transaction.addTag("event", "first_request");
//                 transaction.addTag("path", requestPath);
//                 transaction.addTag("response", responseBody);
//             } finally {
//                 transaction.end();
//             }
            
//             isFirstRequest = false;
//         }
//     }
// } 