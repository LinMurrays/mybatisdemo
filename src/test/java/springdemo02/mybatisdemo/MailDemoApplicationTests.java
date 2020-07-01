package springdemo02.mybatisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class MailDemoApplicationTests {

    @Autowired
    JavaMailSenderImpl javaMailSender;

       @Test
    public void test() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject("通知-今晚开会");
        message.setText("今晚7:30开会");

        message.setTo("linmurrays@foxmail.com");
        message.setFrom("linmurrays@yeah.net");

        javaMailSender.send(message);
    }

    @Test
    public void test2() throws Exception {
        //创建一个复杂的邮件
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);

        //邮件设置
        mimeMessageHelper.setSubject("通知-今晚开会");
        mimeMessageHelper.setText("<b style='color:red'>今天 7:30 开会</b>",true);

        mimeMessageHelper.setTo("linmurrays@foxmail.com");
        mimeMessageHelper.setFrom("linmurrays@yeah.net");
        //上传文件
        mimeMessageHelper.addAttachment("_cK-VpBmJVU.jpg",new File("/Users/linmurrays/Pictures/pap.er/_cK-VpBmJVU.jpg"));
          javaMailSender.send(mimeMailMessage);
    }


}
