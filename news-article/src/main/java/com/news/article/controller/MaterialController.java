package com.news.article.controller;

import com.news.common.vo.RespVo;
import com.news.article.service.MaterialService;
import com.news.article.vo.MaterialVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // 素材列表
    @GetMapping("/list")
    public RespVo findMaterials(Long categoryId, Integer page, Integer pageSize) {
        MaterialVo materialVo = new MaterialVo();
        materialVo.setPage(page);
        materialVo.setPageSize(pageSize);
        materialVo.setCategoryId(categoryId);
        return materialService.findList(materialVo);
    }

    // 新增
    @PostMapping("/upload")
    public RespVo uploadMaterials(@RequestParam MultipartFile file, Long categoryId) {
        return materialService.upload(file, categoryId);
    }

    @GetMapping("/download")
    public void download(Long id, HttpServletResponse response) throws IOException {
         materialService.download(id, response);
    }

    // 删除
    @DeleteMapping("/{id}")
    public RespVo removeMaterial(@PathVariable("id") Long id) {
        return materialService.remove(id);
    }

}
