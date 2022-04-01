package com.hsccc.myspringbootstarter.service.impl;

import com.hsccc.myspringbootstarter.model.entity.Menu;
import com.hsccc.myspringbootstarter.mapper.MenuMapper;
import com.hsccc.myspringbootstarter.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author hsccc
 * @since 2022-03-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
