package io.niufen.springboot.redis;

import io.niufen.springboot.redis.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisLockTest {

    @Test
    public void testRedis() throws InterruptedException {
        String key = "18522178455";
        int totalThreads = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);

        CountDownLatch countDownLatch = new CountDownLatch(totalThreads);
        for(int i=0; i<totalThreads; i++) {
            String threadId = String.valueOf(i);
            executorService.execute( () -> {
                try {
                    testLock(key, threadId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        // After all thread done, acquire again, expect to be successful.
        testLock(key, "final success");
    }

    public void testLock(String key, String threadId) throws InterruptedException {
        boolean lock = RedisLock.lock(key);
        if (lock) {
            log.debug("Successfully got lock - " + threadId);
            Thread.sleep(2000);
            RedisLock.unlock(key);
        } else {
            log.debug("Failed to obtain lock - " + threadId);
        }
    }

    @Test
    public void testWhileRedis() throws InterruptedException {
        String key = "18522178455";
        int totalThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);

        CountDownLatch countDownLatch = new CountDownLatch(totalThreads);
        for(int i=0; i<totalThreads; i++) {
            String threadId = String.valueOf(i);
            executorService.execute( () -> {
                try {
                    testWhileLock(key, threadId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        // After all thread done, acquire again, expect to be successful.
        testLock(key, "final success");
    }

    public void testWhileLock(String key, String threadId) throws InterruptedException {
        boolean lock = false;
        while (!lock) {
            lock = RedisLock.lock(key);
        }
        log.debug("Successfully got lock - " + threadId);
        RedisLock.unlock(key);
    }

}
