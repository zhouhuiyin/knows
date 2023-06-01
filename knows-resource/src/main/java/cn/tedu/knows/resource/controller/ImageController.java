package cn.tedu.knows.resource.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
//当前控制器路径设计为/file
//但是本项目的配置影响，实际调用时要在前面添加/image
//所以实际访问路径为:localhost:9000/image/file
@RequestMapping("/file")
@Slf4j
//跨域注解
@CrossOrigin
public class ImageController {
    // 从application.properties文件中获得配置数据的注解
    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;

    //文件上传的方法
    @PostMapping()
    public String upload(MultipartFile imageFile) throws IOException {
        //1.确定文件保存的路径
        //  会将不同日期的文件保存在不同的文件夹中,所以使用当前年月日组成文件夹名
        // path:2022/01/04
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        //确定要上传的文件夹路径对象
        //d://upload/2022/11/01
        File folder = new File(resourcePath,path);
        //创建文件夹
        folder.mkdirs();
        //2.确定文件名
        //获得原始文件名以截取文件后缀名
        String filename=imageFile.getOriginalFilename();//原始文件名
        //截取后缀名
        String ext = filename.substring(filename.lastIndexOf("."));
        //创建随机新文件名以尽量减少文件名重复几率
        String name = UUID.randomUUID().toString()+ext;
        //3.执行上传
        //实例化要保存的文件对象（路径名+文件名的对象）
        File file = new File(folder,name);
        log.debug("文件上传路径：{}",file.getAbsolutePath());
        //执行上传
        imageFile.transferTo(file);

        // 我们要实现回显,必须得到上传的文件在静态资源服务器的路径
        // 例如  http://localhost:8899/2022/01/04/dac23as....aa0d.jpg
        //         resourceHost    /     path   /      name
        // 我们拼接获得访问这个上传文件的url
        String url=resourceHost+"/"+path+"/"+name;
        log.debug("回显上传图片的url:{}",url);
        //4.返回结果
        return url;

    }

}
