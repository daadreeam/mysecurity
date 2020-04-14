import com.lsw.lswcommon.CommonApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 主要来测试rabbitmq死信队列 实现延时消息
 */
@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestRabbit {
}
