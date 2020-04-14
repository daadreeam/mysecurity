import com.lsw.lswcommon.CommonApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestKafka {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @Test
    public void test(){
        kafkaTemplate.send("msg-topic2",1, "key1","这是第一条消息 发送到partition1");
        kafkaTemplate.send("msg-topic2",1, "key2","这是第一条消息 发送到partition2");
    }
}
