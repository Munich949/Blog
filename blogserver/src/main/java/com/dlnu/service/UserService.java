package com.dlnu.service;

import com.dlnu.mapper.RolesMapper;
import com.dlnu.mapper.UserMapper;
import com.dlnu.pojo.Role;
import com.dlnu.pojo.User;
import com.dlnu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 这个类是 UserService，是一个服务类，用于处理与用户相关的业务逻辑。
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 根据用户名加载用户信息。
     *
     * @param s 用户名。
     * @return 用户对象。
     * @throws UsernameNotFoundException 如果用户不存在。
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            // 避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        // 查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    /**
     * 用户注册。
     *
     * @param user 用户对象。
     * @return 注册结果：0表示成功，1表示用户名重复，2表示失败。
     */
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        // 插入用户,插入之前先对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // 用户可用
        long result = userMapper.reg(user);
        // 配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * 更新用户邮箱。
     *
     * @param email 邮箱。
     * @return 受影响的行数。
     */
    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    /**
     * 根据昵称获取用户列表。
     *
     * @param nickname 昵称。
     * @return 用户列表。
     */
    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    /**
     * 获取所有角色列表。
     *
     * @return 角色列表。
     */
    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    /**
     * 更新用户启用状态。
     *
     * @param enabled 是否启用。
     * @param uid     用户ID。
     * @return 受影响的行数。
     */
    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    /**
     * 根据用户ID删除用户。
     *
     * @param uid 用户ID。
     * @return 受影响的行数。
     */
    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    /**
     * 更新用户角色。
     *
     * @param rids 角色ID数组。
     * @param id   用户ID。
     * @return 受影响的行数。
     */
    public int updateUserRoles(Long[] rids, Long id) {
        int i = userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    /**
     * 根据用户ID获取用户信息。
     *
     * @param id 用户ID。
     * @return 用户对象。
     */
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }
}