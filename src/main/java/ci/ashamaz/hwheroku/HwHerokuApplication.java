package ci.ashamaz.hwheroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HwHerokuApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwHerokuApplication.class, args);
    }

}
