import com.lsw.lswcommon.CommonApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
public class TestRedisSentinel {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 400; i++) {
            String kkk = null;
            try {
                redisTemplate.opsForValue().set("kkk","kkk");
                kkk = redisTemplate.opsForValue().get("kkk");
                System.out.println("次数: "+ i +" 操作redis成功kkk = " + kkk);
            } catch (Exception e) {
                System.out.println("次数: "+ i +" !!!!操作redis失败!!!!");;
            }

            Thread.sleep(2000);
        }
    }
}
