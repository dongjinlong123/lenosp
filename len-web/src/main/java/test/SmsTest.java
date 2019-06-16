package test;

import com.len.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SmsTest {
    @Autowired
    private JavaMailSender mailSender;
    @Test
    public void test(){
        MimeMessage message = mailSender.createMimeMessage();// 新建邮件
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);// 编辑发送内容
        try {
            messageHelper.setFrom("904118787@qq.com");
            messageHelper.setTo("316799047@qq.com");
            messageHelper.setSubject("主题");
            messageHelper.setText("内容");
        } catch (MessagingException e) {

            e.printStackTrace();
        }
        System.out.println("发送成功");
        mailSender.send(message);// 发送
    }


    @Test
    public void test2() {
        MimeMessage message = mailSender.createMimeMessage();// 新建邮件
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);// 编辑发送内容
        try {
            messageHelper.setFrom("904118787@qq.com");
            messageHelper.setTo("316799047@qq.com");
            messageHelper.setSubject("主题");

            // 创建图片"节点"
            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource("F:\\微信图片_20181009180226.jpg"));
            // 将图片数据添加到"节点"
            image.setDataHandler(dh);
            // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
            image.setContentID("mailImgId");

            // 6. 创建文本"节点"
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("这是一张图片<br/><a href='http://www.baidu.com'><img src='cid:mailImgId'/></a>", "text/html;charset=UTF-8");
            // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
            MimeMultipart mm_text_image = new MimeMultipart();
            mm_text_image.addBodyPart(text);
            mm_text_image.addBodyPart(image);
            mm_text_image.setSubType("related");    // 关联关系
            // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
            // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
            // 上面的 mailImgId 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
            MimeBodyPart text_image = new MimeBodyPart();
            text_image.setContent(mm_text_image);
            // 9. 创建附件"节点"
            MimeBodyPart attachment = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh2 = new DataHandler(new FileDataSource("F:\\微信图片_20181009180226.jpg"));
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dh2);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

            // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text_image);
            mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
            mm.setSubType("mixed");         // 混合关系
            // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
            message.setContent(mm);

        } catch (MessagingException e) {

            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("发送成功");
        mailSender.send(message);// 发送
    }
}
