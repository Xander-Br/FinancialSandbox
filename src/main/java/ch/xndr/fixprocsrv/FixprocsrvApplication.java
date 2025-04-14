package ch.xndr.fixprocsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.ApplicationModule;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FixprocsrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixprocsrvApplication.class, args);

    }

    public void modulesList(){
    }

}
