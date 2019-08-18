import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.CmfzHuanghhApplication;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.City;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: untitled
 * @description: 测试类
 * @author: Mr.huang
 * @create: 2019-08-05 17:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHuanghhApplication.class)
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Test
    public void test1(){
        userService.exportAll();
    }
    @Test
    public void test2(){
        userService.importUsers();
    }
    @Test
    public void test3(){
        Date date = new Date();
        String year=String.format("%tY", date);

        String mon=String .format("%tm", date);

        String day=String .format("%td", date);

        System.out.println(year);

        System.out.println(mon);

        System.out.println(day);
    }

    @Test
    public void test4(){
        List<City> citiesBoy = userDao.selectManGroupByCity();
        for (City city : citiesBoy) {
            System.out.println(city);
        }
        List<City> citiesGirl = userDao.selectWomanGroupByCity();
        for (City city : citiesGirl) {
            System.out.println(city);
        }
    }

    @Test
    public void test5(){
        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-5bf80b1620d54596b737f5db10f8136b");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("channel1", "Hello, GoEasy!");
    }

    @Test
    public void test6(){
        userService.showGoEasy();
    }
    @Test
    public void testAdd(){
        User user = new User();
        user.setName("gggg");
        user.setPassword("8888");
        userService.add(user);
    }
}