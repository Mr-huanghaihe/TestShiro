import com.baizhi.CmfzHuanghhApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHuanghhApplication.class)
public class AdminTest {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminService adminService;
    @Test
    public void test1(){
        Admin huang = adminDao.queryShiroByName("huang");
        System.out.println(huang);
    }
    @Test
    public void test2(){
        Admin admin = new Admin();
    }

}
