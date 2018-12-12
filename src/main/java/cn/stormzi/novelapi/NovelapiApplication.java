package cn.stormzi.novelapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NovelapiApplication {

    public static boolean useCache=false;

    protected static final Logger logger = LoggerFactory.getLogger(NovelapiApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(NovelapiApplication.class, args);
    }
}
