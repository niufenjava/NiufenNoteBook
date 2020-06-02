package io.niufen.springboot.redis;

import io.niufen.springboot.common.util.SpringContextUtils;
import io.niufen.common.util.ThreadUtil;
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
public class SmsLimitServiceTest {

    @Autowired
    private SmsLimitService smsLimitService;

    @Test
    public void smsSendLimitVerify() {

        try {
            smsLimitService.smsSendLimitVerify(null);
        }catch (IllegalArgumentException e){
            assert SmsLimitService.SMS_LIMIT_PHONE_VERIFY_MSG.equals(e.getMessage());
        }

        try {
            smsLimitService.smsSendLimitVerify("");
        }catch (IllegalArgumentException e){
            assert SmsLimitService.SMS_LIMIT_PHONE_VERIFY_MSG.equals(e.getMessage());
        }

        try {
            smsLimitService.smsSendLimitVerify("18522131265");
            smsLimitService.smsSendLimitVerify("18522131265");
        }catch (IllegalArgumentException e){
            assert SmsLimitService.SMS_LIMIT_PHONE_LIMIT_MSG.equals(e.getMessage());
        }
        ThreadUtil.sleep(SmsLimitService.SMS_LIMIT_PHONE_LIMIT_MIN);
        smsLimitService.smsSendLimitVerify("18522131265");
    }

    @Test
    public void concurrenceSmsSendLimitVerify() {
        smsLimitService.smsSendLimitVerify("18522131265");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        List<ConcurrenceTest> testList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            testList.add(new ConcurrenceTest(cyclicBarrier));
        }
        Executor executor = Executors.newFixedThreadPool(100);
        for (ConcurrenceTest test : testList) {
            executor.execute(test);
        }
        ThreadUtil.sleep(4);
        smsLimitService.smsSendLimitVerify("18522131265");
    }
}

@Slf4j
class ConcurrenceTest implements Runnable {

    private CyclicBarrier cyclicBarrier;

    @Autowired
    private SmsLimitService smsLimitService;

    public ConcurrenceTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        log.debug(Thread.currentThread().getName()+":就位");
        try {
            cyclicBarrier.await();
            SmsLimitService smsLimitService = (SmsLimitService) SpringContextUtils.getBean("smsLimitService");
            smsLimitService.smsSendLimitVerify("18522131265");
        }catch (IllegalArgumentException | InterruptedException | BrokenBarrierException e){
            assert SmsLimitService.SMS_LIMIT_PHONE_LIMIT_MSG.equals(e.getMessage());
        }
    }
}
