package com.liuwei.user;

import com.liuwei.framework.domain.bo.Content;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.Status;
import com.liuwei.user.domain.dto.add.TopicAddDTO;
import com.liuwei.user.service.TopicService;
import com.liuwei.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JavaWebApplicationTest {
    @Resource
    private UserService userService;

    @Resource
    private TopicService topicService;


    @Test
    public void addUser(){
        User user = new User();
        user.setUsername("hello");
        user.setDateOfBirth(new Date());
        user.setGender(1);
        user.setPassword("123");
        user.setRole(1742094859193335809l);
        user.setStatus(Status.NORMAL_STATUS.getKey());
        user.setCreateTime(new Date());
        user.setDelFlag(Status.NOT_DELETED.getKey());
        userService.save(user);
    }

    @Test
    public void getAllUser(){
        System.out.println(userService.list());
    }

    @Test
    public void addComplexTopic(){
        TopicAddDTO topicAddDTO = new TopicAddDTO();
        topicAddDTO.setIsTop(1);
        topicAddDTO.setStatus(1);
        List<Long> uploadList = new ArrayList<>();
        topicAddDTO.setTitle("养猫的建议");
        topicAddDTO.setCategoryId(1753709564299079682L);
        uploadList.add(1763514355665711106l);
        uploadList.add(1763514377308319746l);
        List<Content> contents = new ArrayList<>();
        Content content1 = new Content();
        Content content2 = new Content();
        Content content3 = new Content();
        contents.add(content3);
        contents.add(content1);
        contents.add(content2);

        content1.setType("image")
                .setContent("1763514355665711106");

        content2.setType("image")
                .setContent("1763514377308319746");
        content3.setType("text").setContent("选择合适的猫咪：根据你的生活环境和生活方式选择适合的猫咪品种，比如，如果你住在小公寓里，可能更适合养适应力强、活动量相对较小的猫咪；如果你有足够的时间和精力陪伴，活泼好动的猫咪会是不错的选择。\n" +
                "\n" +
                "健康保障：在决定养猫之前，确保猫咪已接种疫苗，并定期驱虫、体检。购买或领养时，最好能了解其家族病史。\n" +
                "\n" +
                "提供适宜的生活环境：为猫咪准备干净舒适的猫窝，保持家中卫生，提供足够的活动空间并设置猫爬架等玩具以满足其运动需求。另外，要设立专门的猫砂盆区域。\n" +
                "\n" +
                "合理饮食：给猫咪提供营养均衡的猫粮，保证其日常所需的各种营养素。不要随意喂食人类食物，尤其是巧克力、洋葱、酒精等对猫咪有毒的食物。\n" +
                "\n" +
                "社交互动：猫咪虽然独立，但也需要主人的关注与陪伴，每天适当的玩耍和互动有助于增进感情，也有利于猫咪的心理健康。\n" +
                "\n" +
                "绝育手术：除非你有意繁殖，否则建议尽早为猫咪进行绝育手术，这不仅有利于控制流浪猫数量，还有助于减少一些生殖系统疾病的发生。\n" +
                "\n" +
                "定期美容清洁：定期为猫咪梳理毛发，预防毛球症，必要时可带去宠物店洗澡或修剪爪子。\n" +
                "\n" +
                "合法合规养猫：遵守当地的养宠法规，办理相关手续，如养犬猫登记证等。\n" +
                "\n" +
                "紧急情况处理：了解基本的猫咪急救知识，提前找好附近的宠物医院，以防万一。\n" +
                "\n" +
                "耐心爱心：养猫就像抚养孩子一样，需要爱心和耐心，它们可能会淘气、捣乱，但请始终给予理解和关爱。\n" +
                "\n" +
                "以上就是养猫的一些建议，希望对你有所帮助，祝你和你的猫咪共同度过美好的时光！");
        topicAddDTO.setContents(contents);
        topicAddDTO.setUploadList(uploadList);
       for(int i = 0; i <= 100; i++){
//           topicService.addTopic(topicAddDTO);
       }
    }

}
