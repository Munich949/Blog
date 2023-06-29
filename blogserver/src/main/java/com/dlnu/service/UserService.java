package com.dlnu.service;

import com.dlnu.config.MyPasswordEncoder;
import com.dlnu.mapper.RolesMapper;
import com.dlnu.mapper.UserMapper;
import com.dlnu.pojo.Role;
import com.dlnu.pojo.User;
import com.dlnu.utils.MailClient;
import com.dlnu.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.dlnu.utils.CommunityConstant.*;

/**
 * 这个类是 UserService1，是一个服务类，用于处理与用户相关的业务逻辑。
 */
@Slf4j
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    MyPasswordEncoder passwordEncoder;
    @Autowired
    private MailClient mailClient;

    @Value("${blog.path.domain}")
    private String domain;

    /**
     * 根据用户名加载用户信息。
     *
     * @param username 用户名。
     * @return 用户对象。
     * @throws UsernameNotFoundException 如果用户不存在。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        log.info("user1={}", user);
        if (user == null) {
            // 避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        // 查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.selectRolesByUid(user.getId());
        user.setRoles(roles);
        log.info("user2={}", user);
        return user;
    }

    /**
     * 用户注册。
     *
     * @param user 用户对象。
     * @return 注册结果：0表示成功，1表示用户名重复，2表示邮箱重复，3表示失败。
     */
public int register(User user) {
    User user1 = userMapper.selectUserByUsername(user.getUsername());
    if (user1 != null) {
        return 1;
    }
    User user2 = userMapper.selectUserByEmail(user.getEmail());
    if (user2 != null) {
        return 2;
    }
    // 插入用户,插入之前先对密码进行加密
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // 用户先默认不可用 需要激活
    user.setStatus(false);
    // 生成激活码
    user.setActivationCode(Util.generateUUID());
    // 分配随机头像
    user.setUserface(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
    user.setRegTime(LocalDateTime.now());
    int result = userMapper.register(user);
    // 配置用户的角色，默认都是普通用户
    String[] roles = new String[]{"2"};
    int i = rolesMapper.insertRoles(roles, user.getId());
    // 向用户填写的邮箱发一个用于激活账号的邮件
    // 拼接用于激活的url
    String url = domain + "/activation/" + user.getId() + "/" + user.getActivationCode();
    mailClient.sendMail(user.getEmail(), "激活账号", url);
    boolean b = i == roles.length && result == 1;
    if (b) {
        return 0;
    } else {
        return 3;
    }
}

    public int activation(Long userId, String code) {
        User user = userMapper.selectUserById(userId);
        if (user.getStatus()) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateUserStatus(true, userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
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
        return userMapper.selectUserByNickname(nickname);
    }

    /**
     * 获取所有角色列表。
     *
     * @return 角色列表。
     */
    public List<Role> getAllRole() {
        return rolesMapper.selectAllRole();
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
        userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    /**
     * 根据用户ID获取用户信息。
     *
     * @param id 用户ID。
     * @return 用户对象。
     */
    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    public int updateUserStatus(Boolean status, Long uid) {
        return userMapper.updateUserStatus(status, uid);
    }
}