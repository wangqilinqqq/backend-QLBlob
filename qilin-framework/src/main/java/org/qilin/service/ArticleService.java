package org.qilin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult articleDetail(Long id);
}
