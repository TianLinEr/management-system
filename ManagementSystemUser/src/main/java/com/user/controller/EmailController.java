package com.user.controller;

import com.base.annotation.NotNeedIntercept;
import com.base.context.BaseContext;
import com.base.entity.Tasks;
import com.base.entity.Users;
import com.base.utils.RandomStringGenerator;
import com.base.utils.Result;
import com.base.vo.EmailVO;
import com.base.vo.UserVO;
import com.http.client.TaskHttp;
import com.http.client.UserHttp;
import com.service.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@RestController
@Tag(name = "邮箱管理")
@Slf4j
public class EmailController {

    @Autowired
    private UsersService usersService;

    @Autowired
    public JavaMailSender javaMailSender;// 从容器中拿到 邮件发送对象


    @PostMapping("/sendEmail")
    @Operation(summary = "发送邮件")
    public String send(@RequestBody EmailVO emailVO){
        Integer id = BaseContext.getCurrentId();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(id);
        Users users = usersService.selectById(list).get(0);
        // new一个 简单邮件对象
        SimpleMailMessage mail = new SimpleMailMessage();

        // 设置邮件对象的各个属性，构造成一个较为完整的邮件对象
        mail.setFrom("2370453803@qq.com");// 发邮件的邮箱地址，从资源可直接提取
        mail.setTo(emailVO.getToEmail());// 给谁发，通过参数进行传递
        mail.setText("用户："+users.getUserName()+"发送给您的邮件\r\n"+emailVO.getContent());// 设置邮件的文本内容
        mail.setSubject(emailVO.getTitle());// 设置邮件的主题

        javaMailSender.send(mail);// 进行发送

        log.info("邮件发送成功");
        return "邮件发送成功";
    }

    @GetMapping("/loginCode/{email}")
    @Operation(summary = "注册验证码")
    @NotNeedIntercept
    public String loginEmail(@PathVariable String email){
        // new一个 简单邮件对象
        SimpleMailMessage mail = new SimpleMailMessage();

        // 设置邮件对象的各个属性，构造成一个较为完整的邮件对象
        mail.setFrom("2370453803@qq.com");// 发邮件的邮箱地址，从资源可直接提取
        mail.setTo(email);// 给谁发，通过参数进行传递

        String s = RandomStringGenerator.generateRandomString(8);

        mail.setText("您的验证码为：\r\n"+s+"\r\n请您在两分钟以内完成注册，超时注册码将销毁");// 设置邮件的文本内容
        mail.setSubject("注册验证码");// 设置邮件的主题

        javaMailSender.send(mail);// 进行发送

        HashMap<LocalDateTime, String> map = new HashMap<>();
        map.put(LocalDateTime.now().plusMinutes(2), s);
        BaseContext.setTime(map);

        log.info("邮件发送成功");
        return "邮件发送成功";
    }

    @Autowired
    private TaskHttp taskHttp;
    @Autowired
    private UserHttp userHttp;

    @Transactional
    @Scheduled(cron = "* * 0-4 * * ? ")
    public void sendEmail(){
        Result<Tasks> all = taskHttp.getAll();
        LocalDateTime now = LocalDateTime.now().plus(4, ChronoUnit.HOURS);
        all.getRes().forEach(task -> {
            if(task.getExpiryDate().before(Timestamp.valueOf(now))){
                UserVO users = userHttp.getUserInfo(Collections.singletonList(task.getWorkUserId())).getRes().get(0);
                SimpleMailMessage mail = new SimpleMailMessage();

                mail.setFrom("2370453803@qq.com");// 发邮件的邮箱地址，从资源可直接提取
                mail.setTo(users.getUserEmail());// 给谁发，通过参数进行传递
                mail.setText("管理员提醒您\r\n"+"您的待办任务即将到期，请尽快提交。");// 设置邮件的文本内容
                mail.setSubject("任务即将到期");// 设置邮件的主题

                javaMailSender.send(mail);// 进行发送
            }
        });
    }
}
