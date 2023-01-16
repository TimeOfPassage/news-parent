package com.news.article.controller;

import com.news.article.service.ArticleService;
import com.news.article.service.MaterialService;
import com.news.article.vo.DocumentVo;
import com.news.article.vo.MaterialVo;
import com.news.common.vo.RespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/document")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public RespVo findList(String title, Integer page, Integer pageSize) {
        DocumentVo vo = new DocumentVo();
        vo.setPage(page);
        vo.setPageSize(pageSize);
        vo.setTitle(title);
        return articleService.findList(vo);
    }

    @GetMapping("/{id}")
    public RespVo get(@PathVariable("id") Long id) {
        return articleService.getArticle(id);
    }

    // 新增
    @PostMapping("/add")
    public RespVo add(@RequestBody DocumentVo documentVo) {
        return articleService.add(documentVo);
    }

    @GetMapping("/modify")
    public RespVo modify(@RequestBody DocumentVo documentVo) {
        return articleService.modify(documentVo);
    }

    // 删除
    @DeleteMapping("/{id}")
    public RespVo remove(@PathVariable("id") Long id) {
        return articleService.remove(id);
    }

}
