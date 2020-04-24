import com.lsw.craw.CrawApplication;
import com.lsw.craw.demo.SimpleCrawler;
import com.lsw.craw.entity.JdPhone;
import com.lsw.craw.mapper.JdPhoneMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest(classes = CrawApplication.class)
@RunWith(SpringRunner.class)
public class MbTest {

    @Autowired
    private JdPhoneMapper jdPhoneMapper;

    @Autowired
    private SimpleCrawler simpleCrawler;

    @Test
    public void testMapper(){
//        JdPhone jdPhone = jdPhoneMapper.selectByPrimaryKey(1L);
//        System.out.println("jdPhone = " + jdPhone);
        JdPhone build = JdPhone.builder().title("huawei p455").build();
        JdPhone build2 = JdPhone.builder().title("huawei p488").build();
        List<JdPhone> list = Arrays.asList(build, build2);
//        jdPhoneMapper.insertSelective();
//        jdPhoneMapper.batchInsert(list);
        jdPhoneMapper.insertSelective(build);
    }

    @Test
    public void testCrawler(){

        int j = 1;
        for (int i = 1; i < 200; i+=2,j++) {
            //psort=3是按照销量
            String searchUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&psort=3&page=" + i;

            log.info("现在是是第 " + j + " 页");
            simpleCrawler.crawlerJd(searchUrl);
        }


    }

}
