package live.lslm.newbuckmoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class NewbuckmooApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewbuckmooApplication.class, args);
    }
}