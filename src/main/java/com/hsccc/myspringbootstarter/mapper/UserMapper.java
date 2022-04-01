package com.hsccc.myspringbootstarter.mapper;

import com.hsccc.myspringbootstarter.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author hsccc
 * @since 2022-03-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
