package test;

import com.len.Application;
import com.len.common.WxMessageUtil;
import com.len.service.WxMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class WxTest {
    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxMessageUtil wxMessageUtil;
    @Test
    public void test(){
        //获取accessToken
        wxMessageUtil.getAccessToken();

        //获取菜单信息
        String menuStr = wxMenuService.initMenu();
        //菜单初始化
        wxMessageUtil.initMenu(menuStr);

    }

}
