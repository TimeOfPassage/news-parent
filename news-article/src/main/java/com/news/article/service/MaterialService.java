package com.news.article.service;

import com.news.article.vo.DocumentVo;
import com.news.common.vo.RespVo;
import com.news.article.vo.MaterialVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MaterialService {

    RespVo findList(MaterialVo materialVo);

    RespVo remove(Long id);

    RespVo upload(MultipartFile file, Long categoryId);

    void download(Long id, HttpServletResponse response) throws IOException;
}
