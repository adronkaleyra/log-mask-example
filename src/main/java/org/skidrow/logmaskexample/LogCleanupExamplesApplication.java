package org.skidrow.logmaskexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class LogCleanupExamplesApplication {

    private static final Logger logger = LoggerFactory.getLogger(LogCleanupExamplesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LogCleanupExamplesApplication.class, args);
        logger.info("Hello World!");
    }

    @Scheduled(fixedRate = 2000) // 2000 milliseconds = 2 seconds
    public void logRandomPiiData() {
        String piiData = PiiDataGenerator.generateRandomData();
        logger.info("Generated PII Data: {}", piiData);
        logger.info("More PII Data: {}", PiiDataGenerator.morePii());
    }

}
