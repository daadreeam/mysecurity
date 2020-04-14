import com.lsw.lswcommon.CommonApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestZk {

    @Autowired
    private CuratorFramework zkClient;

    @Test
    public void test1() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    log.info("当前线程名字 = " + name);
                    InterProcessMutex lock = new InterProcessMutex(zkClient, "/mylock");
                    boolean acquire = false;
                    try {
                        acquire = lock.acquire(120, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (acquire) {
                        log.info(name + " 获取锁成功");
                        // do......
                        log.info(name + " 正在处理业务....");
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        log.info(name + " 释放锁成功");
                    } else {
                        log.info(name + " 没有获取到锁");
                    }
                }
            }).start();
        }


    }
}
