package org.qilin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qilin.constants.SystemConstants;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.Article;
import org.qilin.domain.entity.Category;
import org.qilin.domain.vo.CategoryVo;
import org.qilin.domain.vo.HotArticleVo;
import org.qilin.mapper.CategoryMapper;
import org.qilin.service.ArticleService;
import org.qilin.service.CategoryService;
import org.qilin.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-04-24 11:52:26
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult categoryList() {
        // 获取所有文章，状态是已发布的
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);

        // 根据文章列表获取分类id列表，并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 根据获取到的分类id列表，获取分类列表，并且状态是正常的
        List<Category> categoryList = listByIds(categoryIds);
        categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        // 封装vo返回
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
