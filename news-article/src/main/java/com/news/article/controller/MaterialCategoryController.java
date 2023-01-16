package com.news.article.controller;

import com.news.common.vo.RespVo;
import com.news.article.service.MaterialCategoryService;
import com.news.article.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/material")
public class MaterialCategoryController {

    @Autowired
    private MaterialCategoryService materialCategoryService;

    // 分类列表
    @GetMapping("/categories")
    public RespVo findCategories(String name, Integer page, Integer pageSize) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setName(name);
        categoryVo.setPage(page);
        categoryVo.setPageSize(pageSize);
        return materialCategoryService.find(categoryVo);
    }

    @GetMapping("/findAllCategories")
    public RespVo findAllCategories() {
        return materialCategoryService.findAll();
    }

    // 新增
    @PostMapping("/category")
    public RespVo addCategory(@RequestBody CategoryVo categoryVo) {
        return materialCategoryService.add(categoryVo);
    }

    // 修改
    @PutMapping("/category")
    public RespVo modifyCategory(@RequestBody CategoryVo categoryVo) {
        return materialCategoryService.modify(categoryVo);
    }

    // 删除
    @DeleteMapping("/category")
    public RespVo removeCategory(Long id) {
        return materialCategoryService.remove(id);
    }
}
