package org.qilin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.Link;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2023-05-31 11:07:11
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
