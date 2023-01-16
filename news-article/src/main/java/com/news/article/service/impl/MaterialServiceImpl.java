package com.news.article.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjUtil;
import com.news.common.common.UserContextHelper;
import com.news.common.enums.EnableEnum;
import com.news.common.enums.RespCodeEnum;
import com.news.common.exception.CommonException;
import com.news.common.vo.RespVo;
import com.news.storage.StorageService;
import com.news.article.entity.MaterialEntity;
import com.news.article.repository.MaterialRepository;
import com.news.article.service.MaterialService;
import com.news.article.vo.MaterialVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public RespVo findList(MaterialVo materialVo) {
        PageRequest request = PageRequest.of(materialVo.getPage(), materialVo.getPageSize(), Sort.by(Sort.Order.desc("createTime")));
        Page<MaterialEntity> page = materialRepository.findAll((Specification<MaterialEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();
            // 查询条件
            if (ObjUtil.isNotEmpty(materialVo.getCategoryId())) {
                predicatesList.add(criteriaBuilder.equal(root.get("categoryId"), materialVo.getCategoryId()));
            }
            predicatesList.add(criteriaBuilder.equal(root.get("deleteFlag"), EnableEnum.NO.getCode()));
            //最终将查询条件拼好然后return
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));
        }, request);
        return RespVo.page(page);
    }

    @Override
    public RespVo remove(Long id) {
        Optional<MaterialEntity> optionalMaterial = materialRepository.findById(id);
        if (!optionalMaterial.isPresent()) {
            return RespVo.ok(null);
        }
        MaterialEntity material = optionalMaterial.get();
        material.setDeleteFlag((short) EnableEnum.YES.getCode());
        materialRepository.save(material);
        return RespVo.ok(null);
    }

    @Override
    public RespVo upload(MultipartFile file, Long categoryId) {
        try {
            String path = storageService.upload("", "admin_" + file.getOriginalFilename(), file.getInputStream());
            MaterialEntity material = new MaterialEntity();
            material.setCreateTime(System.currentTimeMillis());
            material.setType((short) 0);
            material.setUid(UserContextHelper.get().getUserId());
            material.setCategoryId(categoryId);
            material.setSize(file.getSize());
            material.setFileName(file.getOriginalFilename());
            material.setDeleteFlag((short) EnableEnum.NO.getCode());
            material.setUrl(path);
            return RespVo.ok(materialRepository.save(material));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws IOException {
        Optional<MaterialEntity> optionalMaterial = materialRepository.findById(id);
        if (!optionalMaterial.isPresent()) {
            throw new CommonException(RespCodeEnum.MATERIAL_NOT_EXIST);
        }
        MaterialEntity material = optionalMaterial.get();
        InputStream download = storageService.download("", material.getUrl());
        IoUtil.copy(download, response.getOutputStream());
    }
}
