package org.qilin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qilin.constants.SystemConstants;
import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.Link;
import org.qilin.domain.vo.LinkVo;
import org.qilin.mapper.LinkMapper;
import org.qilin.service.LinkService;
import org.qilin.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 11:07:11
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
