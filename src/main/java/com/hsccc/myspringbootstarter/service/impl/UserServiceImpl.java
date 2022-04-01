package com.hsccc.myspringbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsccc.myspringbootstarter.exception.ApiException;
import com.hsccc.myspringbootstarter.model.dto.UserDetail;
import com.hsccc.myspringbootstarter.model.entity.User;
import com.hsccc.myspringbootstarter.mapper.UserMapper;
import com.hsccc.myspringbootstarter.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author hsccc
 * @since 2022-03-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {
    private final UserMapper userMapper;


    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // TODO: select by userName or email or phone, judge user status
        // TODO: enum impl
        var queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, userName);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new ApiException("用户未注册", HttpStatus.FORBIDDEN);
        } else if ("1".equals(user.getStatus())) {
            throw new ApiException("对不起，您的账号已禁用", HttpStatus.FORBIDDEN);
        }
        // TODO 获取相关权限
        return new UserDetail(user);
    }
}
