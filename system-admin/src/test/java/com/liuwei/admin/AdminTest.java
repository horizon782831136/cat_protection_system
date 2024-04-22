package com.liuwei.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.admin.dao.UserDao;
import com.liuwei.admin.service.CategoryService;
import com.liuwei.admin.service.TopicService;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.domain.po.Topic;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.service.UniqueIdGeneratorService;
import io.minio.*;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AdminTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UniqueIdGeneratorService uniqueIdGeneratorService;
    @Autowired
    private TopicService topicService;
    @Test
    void test(){
        Category category = new Category();
        category.setName("狗");
        category.setDescription("狗");
        category.setParentId(0L);
        category.setStatus(Status.NORMAL_STATUS.getKey());
        category.setImageId(1L);
        category.setCreateTime(new Date());
        category.setDelFlag(Status.NOT_DELETED.getKey());
        categoryService.save(category);
    }

    @Test
    void addCatTest(){
        Category category = new Category();
        category.setName("龙猫");
        category.setDescription("猫");
        category.setParentId(1753709564299079682l);
        category.setStatus(Status.NORMAL_STATUS.getKey());
        category.setImageId(1L);
        category.setCreateTime(new Date());
        category.setDelFlag(Status.NOT_DELETED.getKey());
        categoryService.save(category);
    }



    @Test
    void addTopicTest(){
        for(int i = 0; i < 200; i++){
            Topic topic = new Topic();
            topic.setTitle("喵喵 : " + i);
            topic.setIsTopic(1);
            topic.setUserId(1752757742369554433L);
            topic.setCreateTime(new Date());
            topic.setDelFlag(Status.NOT_DELETED.getKey());
            topic.setCategoryId(1753709845669752833L);
            topic.setCommentCount(new Random().nextInt(100000));
            topic.setClickCount(new Random().nextInt(100000));
            topic.setContent("喵喵球 : " + i + " : ");
            topic.setLikeCount(new Random().nextInt(100000));
            topic.setWeight(1);
            topic.setIsTop(Status.NOT_TOP.getKey());
            topic.setStatus(Status.NORMAL_STATUS.getKey());
            topicService.save(topic);
        }
    }

    @Test
    void addTopicTest2(){
        for(int i = 0; i < 300; i++){
            Topic topic = new Topic();
            topic.setTitle("喵喵叫啊啊 : " + i);
            topic.setIsTopic(1);
            topic.setUserId(1752757742369554433L);
            topic.setCreateTime(new Date());
            topic.setDelFlag(Status.NOT_DELETED.getKey());
            topic.setCategoryId(1753709564299079682L);
            topic.setCommentCount(new Random().nextInt(100000));
            topic.setClickCount(new Random().nextInt(100000));
            topic.setContent("喵喵球蹦蹦 : " + i + " : ");
            topic.setLikeCount(new Random().nextInt(100000));
            topic.setWeight(1);
            topic.setIsTop(Status.NOT_TOP.getKey());
            topic.setStatus(Status.NORMAL_STATUS.getKey());
            topicService.save(topic);
        }
    }

    @Test
    void updateTopicTest(){
        topicService.list().forEach(topic -> {
            topic.setScore(topic.getClickCount() * 0.0004+ topic.getCommentCount() * 0.02 + topic.getLikeCount() * 0.1);
            topicService.updateById(topic);
                });
    }

    @Test
    void testUpload(){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("admin")
                            .object("1.jpg")
                            .filename("E:\\images\\1.jpg")
                            .build()
            );
            System.out.println("ok");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testDownload(){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                    .bucket("admin")
                    .object("1.jpg")
                    .filename("E:\\images\\image.jpg")
                    .build()
            );
            System.out.println("ok");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testDelete(){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("admin")
                            .object("1.jpg")
                            .build()
            );
            System.out.println("ok");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testGetObjectInfo(){
        try{
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
            GetObjectResponse admin = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("admin")
                    .object("2.jpg")
                    .build());
            System.out.println(admin);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testGetObjectByPath(){
        try{
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("CkVZbQmUkIsqS1Lakux9", "RiAUv9cmk8ZKC7udooQXU3m8D8eMLq3AQYhzkerp")
                    .build();
            String get = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .expiry(2, TimeUnit.HOURS)
                    .method(Method.GET)
                    .object("1.jpg")
                    .bucket("admin")
                    .build());
            System.out.println(get);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testGetUploadPresignedObjcetUrl(){
        try{
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("CkVZbQmUkIsqS1Lakux9", "RiAUv9cmk8ZKC7udooQXU3m8D8eMLq3AQYhzkerp")
                    .build();
            String upload = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .expiry(2, TimeUnit.HOURS)
                    .method(Method.PUT)
                    .object("1.jpg")
                    .bucket("admin")
                    .build()
            );
            System.out.println(upload);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testLength(){
        String str = "http://localhost:9000/admin/1.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=CkVZbQmUkIsqS1Lakux9%2F20240216%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240216T155900Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=46f8c8c1bcabf61d1c5ad58782ef0533056d70d9092bbdf3addb0243c0315527";
        System.out.println(str.length());
    }

    @Test
    void getUniqueGeneratorString(){
        System.out.println(uniqueIdGeneratorService.generateUniqueId());
    }

    @Test
    void testRef(){
        class Cat{
            String name;

            public Cat(String name) {
                this.name = name;
            }
        }
        //使用for循环、foreach、基于stream的foreach都可以通过通过引用的方式拿到对象的，即在内部修改对象的值，原体对象的值也会被改变
        List<Cat> list = new ArrayList<>();
        list.add(new Cat("white"));
        list.add(new Cat("orange"));
        list.add(new Cat("black"));

        list.stream().forEach(cat -> {
            if(cat.name.equals("black")){
                cat.name = "yellow";
            }
        });

        list.forEach(e -> System.out.println(e.name));
    }

    @Test
    void testDao(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUserId);
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);

    }



}


