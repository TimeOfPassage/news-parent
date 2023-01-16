package com.news.article.service;

import com.news.common.vo.RespVo;
import com.news.article.vo.CategoryVo;

public interface MaterialCategoryService {
    RespVo add(CategoryVo categoryVo);

    RespVo modify(CategoryVo categoryVo);

    RespVo remove(Long id);

    RespVo find(CategoryVo categoryVo);

    RespVo findAll();
}
