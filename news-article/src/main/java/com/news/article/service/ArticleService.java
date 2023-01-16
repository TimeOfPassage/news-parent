package com.news.article.service;

import com.news.article.vo.DocumentVo;
import com.news.article.vo.MaterialVo;
import com.news.common.vo.RespVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ArticleService {

    RespVo add(DocumentVo documentVo);

    RespVo findList(DocumentVo vo);

    RespVo remove(Long id);

    RespVo modify(DocumentVo documentVo);

    RespVo getArticle(Long id);
}
