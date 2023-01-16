package com.news.article.controller;

import cn.hutool.core.io.IoUtil;
import com.news.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class DemoController {
    @Autowired
    private StorageService storageService;

    @GetMapping("/service")
    public String service() {
        return "Article";
    }

    @PostMapping("/test")
    public String test(@RequestParam MultipartFile file) {
        try {
            return storageService.upload("", file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get")
    public void test(String path, HttpServletResponse response) throws IOException {
        InputStream download = storageService.download("", path);
        IoUtil.copy(download, response.getOutputStream());
    }
}
