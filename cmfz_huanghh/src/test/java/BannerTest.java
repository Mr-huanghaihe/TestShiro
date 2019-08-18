import com.baizhi.CmfzHuanghhApplication;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHuanghhApplication.class)
public class BannerTest {
    @Autowired
    private BannerService bannerService;
    @Test
    public void test1(){
    }
}
