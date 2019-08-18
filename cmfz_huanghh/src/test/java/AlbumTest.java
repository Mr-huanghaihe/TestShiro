import com.baizhi.CmfzHuanghhApplication;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHuanghhApplication.class)
public class AlbumTest {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumDao albumDao;
    @Test
    public void test1(){
        List<Album> albums = albumDao.selectAll(0, 1);
        for (Album album : albums) {
            System.out.println(album);
        }
    }
}
