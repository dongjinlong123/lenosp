package test;

import com.len.Application;
import com.len.entity.HJPerson;
import com.len.entity.SysUser;
import com.len.mapper.SysUserMapper;
import com.len.service.HJPersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class InserTest {
    @Autowired
    private HJPersonService hjPersonService;
    @Test
    public void test(){
        HJPerson hj = new HJPerson();
        hj.setUseName("董锦龙");
        //选择性插入
        Integer i = hjPersonService.insertSelective(hj);
        System.out.println(hj.getUserNum());
        //主键查询
        System.out.println( hjPersonService.selectByPrimaryKey(4595));


    }
}
