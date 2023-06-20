package org.qilin.service;

import org.qilin.domain.ResponseResult;
import org.qilin.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
