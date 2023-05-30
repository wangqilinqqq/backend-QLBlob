package org.qilin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-04-24 11:52:26
 */
public interface CategoryService extends IService<Category> {
    ResponseResult categoryList();
}
