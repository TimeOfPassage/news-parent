package com.news.article.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.news.article.entity.ArticleDetailEntity;
import com.news.article.entity.ArticleEntity;
import com.news.article.entity.MaterialEntity;
import com.news.article.repository.ArticleDetailRepository;
import com.news.article.repository.ArticleRepository;
import com.news.article.service.ArticleService;
import com.news.article.vo.DocumentVo;
import com.news.common.common.UserContextHelper;
import com.news.common.enums.EnableEnum;
import com.news.common.enums.RespCodeEnum;
import com.news.common.vo.RespVo;
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
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleDetailRepository articleDetailRepository;

    @Override
    public RespVo add(DocumentVo documentVo) {
        if (StrUtil.isBlank(documentVo.getTitle())) {
            return RespVo.error(RespCodeEnum.ARTICLE_TITLE_NOT_NULL);
        }
        if (StrUtil.isBlank(documentVo.getContent())) {
            return RespVo.error(RespCodeEnum.ARTICLE_CONTENT_NOT_NULL);
        }
        ArticleEntity query = new ArticleEntity();
        query.setTitle(documentVo.getTitle());
        query.setUid(UserContextHelper.get().getUserId());
        query.setDeleteFlag((short) EnableEnum.NO.getCode());
        if (articleRepository.count(Example.of(query)) > 0) {
            return RespVo.error(RespCodeEnum.ARTICLE_TITLE_REPEAT);
        }
        ArticleEntity article = new ArticleEntity();
        article.setUid(UserContextHelper.get().getUserId());
        article.setType((short) 0);
        article.setCreateTime(System.currentTimeMillis());
        article.setDeleteFlag((short) EnableEnum.NO.getCode());
        article.setTitle(documentVo.getTitle());
        ArticleEntity save = articleRepository.save(article);
        ArticleDetailEntity articleDetail = new ArticleDetailEntity();
        articleDetail.setArticleId(save.getId());
        articleDetail.setContent(documentVo.getContent());
        articleDetail.setContentHtml(documentVo.getContentHtml());
        articleDetailRepository.save(articleDetail);
        return RespVo.ok(save);
    }

    @Override
    public RespVo findList(DocumentVo vo) {
        PageRequest request = PageRequest.of(vo.getPage(), vo.getPageSize(), Sort.by(Sort.Order.desc("createTime")));
        Page<ArticleEntity> page = articleRepository.findAll((Specification<ArticleEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();
            // 查询条件
            if (ObjUtil.isNotEmpty(vo.getTitle())) {
                predicatesList.add(criteriaBuilder.like(root.get("title"), "%" + vo.getTitle() + "%"));
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
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (!optional.isPresent()) {
            return RespVo.ok(null);
        }
        ArticleEntity article = optional.get();
        article.setDeleteFlag((short) EnableEnum.YES.getCode());
        articleRepository.save(article);
        return RespVo.ok(null);
    }

    @Override
    public RespVo modify(DocumentVo documentVo) {
        Optional<ArticleEntity> optional = articleRepository.findById(documentVo.getId());
        if (!optional.isPresent()) {
            return RespVo.error(RespCodeEnum.ARTICLE_NOT_EXIST);
        }
        ArticleEntity query = new ArticleEntity();
        query.setTitle(documentVo.getTitle());
        query.setUid(UserContextHelper.get().getUserId());
        query.setDeleteFlag((short) EnableEnum.NO.getCode());
        List<ArticleEntity> articles = articleRepository.findAll(Example.of(query));
        if (CollUtil.isNotEmpty(articles) && articles.size() > 1 && articles.get(0).getId() != documentVo.getId()) {
            return RespVo.error(RespCodeEnum.ARTICLE_TITLE_REPEAT);
        }
        ArticleEntity article = optional.get();
        article.setTitle(documentVo.getTitle());
        articleRepository.save(article);
        ArticleDetailEntity articleDetail = new ArticleDetailEntity();
        articleDetail.setArticleId(article.getId());
        Optional<ArticleDetailEntity> detailOption = articleDetailRepository.findOne(Example.of(articleDetail));
        if (detailOption.isPresent()) {
            ArticleDetailEntity articleDetailFromDB = detailOption.get();
            articleDetailFromDB.setContent(documentVo.getContent());
            articleDetailFromDB.setContentHtml(documentVo.getContentHtml());
            articleDetailRepository.save(articleDetailFromDB);
        } else {
            articleDetail = new ArticleDetailEntity();
            articleDetail.setArticleId(documentVo.getId());
            articleDetail.setContent(documentVo.getContent());
            articleDetail.setContentHtml(documentVo.getContentHtml());
            articleDetailRepository.save(articleDetail);
        }
        return RespVo.ok(null);
    }

    @Override
    public RespVo getArticle(Long id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (!optional.isPresent()) {
            return RespVo.error(RespCodeEnum.ARTICLE_NOT_EXIST);
        }
        DocumentVo vo = new DocumentVo();
        vo.setId(id);
        ArticleEntity article = optional.get();
        vo.setTitle(article.getTitle());
        ArticleDetailEntity articleDetail = new ArticleDetailEntity();
        articleDetail.setArticleId(article.getId());
        Optional<ArticleDetailEntity> detailOption = articleDetailRepository.findOne(Example.of(articleDetail));
        if (detailOption.isPresent()) {
            ArticleDetailEntity detail = detailOption.get();
            vo.setContent(detail.getContent());
            vo.setContentHtml(detail.getContentHtml());
        }
        return RespVo.ok(vo);
    }
}
