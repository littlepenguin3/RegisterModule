package sysu.usc.registerModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 9:06
 */
@SpringBootApplication


public class RegisterModuleApplication {

    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPool thread: " + integer.getAndIncrement());
        }
    };
    @Bean
    ThreadPoolExecutor newFixedThreadPool(){
        return new ThreadPoolExecutor(8, 8, 2, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(512),threadFactory
                );
    }
    public static void main(String[] args) {
        SpringApplication.run(RegisterModuleApplication.class, args);
    }

}
