package com.news.article.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.news.common.common.UserContextHelper;
import com.news.common.enums.RespCodeEnum;
import com.news.common.vo.RespVo;
import com.news.article.entity.CategoryEntity;
import com.news.article.repository.CategoryRepository;
import com.news.article.service.MaterialCategoryService;
import com.news.article.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public RespVo add(CategoryVo categoryVo) {
        if (StrUtil.isBlank(categoryVo.getName())) {
            return RespVo.error(RespCodeEnum.CATEGORY_NAME_NOT_NULL);
        }
        // 判断是否存在
        CategoryEntity queryEntity = new CategoryEntity();
        queryEntity.setName(categoryVo.getName());
        if (categoryRepository.count(Example.of(queryEntity)) > 0) {
            return RespVo.error(RespCodeEnum.CATEGORY_NAME_EXIST);
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryVo.getName());
        categoryEntity.setUid(UserContextHelper.get().getUserId());
        categoryEntity.setCreateTime(System.currentTimeMillis());
        return RespVo.ok(categoryRepository.save(categoryEntity));
    }

    @Override
    public RespVo modify(CategoryVo categoryVo) {
        if (ObjUtil.isEmpty(categoryVo.getId())) {
            return RespVo.error(RespCodeEnum.CATEGORY_ID_NOT_NULL);
        }
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryVo.getId());
        if (!optionalCategory.isPresent()) {
            return RespVo.error(RespCodeEnum.CATEGORY_NOT_EXIST);
        }
        CategoryEntity category = optionalCategory.get();
        if (StrUtil.isBlank(categoryVo.getName())) {
            return RespVo.error(RespCodeEnum.CATEGORY_NAME_NOT_NULL);
        }
        // 判断是否存在
        CategoryEntity queryEntity = new CategoryEntity();
        queryEntity.setName(categoryVo.getName());
        if (categoryRepository.count(Example.of(queryEntity)) > 0) {
            return RespVo.error(RespCodeEnum.CATEGORY_NAME_EXIST);
        }
        category.setName(categoryVo.getName());
        return RespVo.ok(categoryRepository.save(category));
    }

    @Override
    public RespVo remove(Long id) {
        if (ObjUtil.isEmpty(id)) {
            return RespVo.error(RespCodeEnum.CATEGORY_ID_NOT_NULL);
        }
        categoryRepository.deleteById(id);
        return RespVo.ok(null);
    }

    @Override
    public RespVo find(CategoryVo categoryVo) {
        PageRequest request = PageRequest.of(categoryVo.getPage(), categoryVo.getPageSize(), Sort.by(Sort.Order.desc("createTime")));
        Page<CategoryEntity> page = categoryRepository.findAll((Specification<CategoryEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();
            // 查询条件
            if (StrUtil.isNotBlank(categoryVo.getName())) {
                Predicate namePredicate = criteriaBuilder.equal(root.get("name"), categoryVo.getName());
                predicatesList.add(namePredicate);
            }
            //最终将查询条件拼好然后return
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));
        }, request);
        return RespVo.page(page);
    }

    @Override
    public RespVo findAll() {
        return RespVo.ok(categoryRepository.findAll());
    }
}
