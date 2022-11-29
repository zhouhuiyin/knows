package cn.tedu.knows.faq.controller;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/v2/tags")
public class TagController {
    @Autowired
    private ITagService tagService;
    //@GetMapping("")的意思是直接使用类上声明的路径访问当前控制器方法
    // localhost:8080/v1/tags即可访问
    @GetMapping("")
    public List<Tag> tags(){
        return tagService.getTags();
    }


}
