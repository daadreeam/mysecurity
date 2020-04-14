import com.lsw.lswcommon.CommonApplication;
import com.lsw.lswcommon.es.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
public class TestEs {

    @Autowired
    private IndexService indexService;

    @Test
    public void test1(){

//        indexService.createIndex();
        indexService.addDocument();
        indexService.getDocument();
        indexService.updateDocument();
        indexService.getDocument();
//        indexService.deleteDocument();
//        indexService.getDocument();
    }

}
