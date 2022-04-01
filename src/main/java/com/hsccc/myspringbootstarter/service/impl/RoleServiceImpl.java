package com.hsccc.myspringbootstarter.service.impl;

import com.hsccc.myspringbootstarter.model.entity.Role;
import com.hsccc.myspringbootstarter.mapper.RoleMapper;
import com.hsccc.myspringbootstarter.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author hsccc
 * @since 2022-03-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
