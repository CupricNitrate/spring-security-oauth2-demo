package com.cupricnitrate.authority.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupricnitrate.authority.mapper.UserMapper;
import com.cupricnitrate.authority.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 硝酸铜
 * @date 2021/9/22
 */
@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return baseMapper.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户名为 " + s + "的用户"));
    }
}
