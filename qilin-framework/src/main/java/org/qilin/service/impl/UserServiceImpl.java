package org.qilin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qilin.domain.entity.User;
import org.qilin.mapper.UserMapper;
import org.qilin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 16:32:45
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
