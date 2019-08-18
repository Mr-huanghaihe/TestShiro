import com.baizhi.CmfzHuanghhApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHuanghhApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test1(){
        redisTemplate.opsForHash().put("names","name1","huang");
        redisTemplate.opsForHash().put("names","name2","haihe");
        Boolean hasKey = redisTemplate.opsForHash().hasKey("names", "name1");
        System.out.println(hasKey);
        Object o = redisTemplate.opsForHash().get("names", "name1");
        System.out.println(o);
    }
}
