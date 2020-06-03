package io.niufen.springboot.redis;

import io.niufen.common.core.thread.ThreadUtil;
import io.niufen.springboot.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginLimitServiceTest {

    @Autowired
    private LoginLimitService loginLimitService;

    @Test
    public void smsSendLimitVerify() {

        try {
            loginLimitService.userLoginLimitVerify(null);
        }catch (IllegalArgumentException e){
            assert LoginLimitService.LOGIN_LIMIT_ACCOUNT_VERIFY_MSG.equals(e.getMessage());
        }

        try {
            loginLimitService.userLoginLimitVerify("");
        }catch (IllegalArgumentException e){
            assert LoginLimitService.LOGIN_LIMIT_ACCOUNT_VERIFY_MSG.equals(e.getMessage());
        }

        loginLimitService.userLoginLimitVerify("18522131265");
        loginLimitService.userLoginLimitVerify("18522131265");
        loginLimitService.userLoginLimitVerify("18522131265");
        loginLimitService.userLoginLimitVerify("18522131265");
        loginLimitService.userLoginLimitVerify("18522131265");

        try {
            loginLimitService.userLoginLimitVerify("18522131265");
        }catch (IllegalArgumentException e){
            assert LoginLimitService.LOGIN_LIMIT_ACCOUNT_LIMIT_MSG.equals(e.getMessage());
        }
        ThreadUtil.sleep(LoginLimitService.LOGIN_LIMIT_ACCOUNT_LIMIT_MIN);
        loginLimitService.userLoginLimitVerify("18522131265");
    }

    @Test
    public void concurrenceSmsSendLimitVerify() {
        loginLimitService.userLoginLimitVerify("18522131265");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        List<ConcurrenceTest> testList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            testList.add(new ConcurrenceTest(cyclicBarrier));
        }
        Executor executor = Executors.newFixedThreadPool(100);
        for (ConcurrenceTest test : testList) {
            executor.execute(test);
        }
        ThreadUtil.sleep(6);
        loginLimitService.userLoginLimitVerify("18522131265");
    }
}

@Slf4j
class ConcurrenceLoginTest implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public ConcurrenceLoginTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        log.debug(Thread.currentThread().getName()+":就位");
        try {
            cyclicBarrier.await();
            LoginLimitService loginLimitService = (LoginLimitService) SpringContextUtils.getBean("loginLimitService");
            loginLimitService.userLoginLimitVerify("18522131265");
        }catch (IllegalArgumentException | InterruptedException | BrokenBarrierException e){
            assert SmsLimitService.SMS_LIMIT_PHONE_LIMIT_MSG.equals(e.getMessage());
        }
    }
}
