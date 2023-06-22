package com.dlnu.mapper;

import com.dlnu.pojo.Role;
import com.dlnu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 这个接口表示 User 实体的 Mapper。
 * 它负责在数据库中执行与用户相关的 CRUD 操作。
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名加载用户信息。
     *
     * @param username 用户名。
     * @return 用户对象。
     */
    User loadUserByUsername(@Param("username") String username);

    /**
     * 用户注册。
     *
     * @param user 用户对象。
     * @return 受影响的行数。
     */
    long reg(User user);

    /**
     * 更新用户邮箱。
     *
     * @param email 邮箱。
     * @param id    用户ID。
     * @return 受影响的行数。
     */
    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    /**
     * 根据昵称获取用户列表。
     *
     * @param nickname 昵称。
     * @return 用户列表。
     */
    List<User> getUserByNickname(@Param("nickname") String nickname);

    /**
     * 获取所有角色列表。
     *
     * @return 角色列表。
     */
    List<Role> getAllRole();

    /**
     * 更新用户启用状态。
     *
     * @param enabled 是否启用。
     * @param uid     用户ID。
     * @return 受影响的行数。
     */
    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    /**
     * 根据用户ID删除用户。
     *
     * @param uid 用户ID。
     * @return 受影响的行数。
     */
    int deleteUserById(Long uid);

    /**
     * 根据用户ID删除用户角色关联。
     *
     * @param id 用户ID。
     * @return 受影响的行数。
     */
    int deleteUserRolesByUid(Long id);

    /**
     * 设置用户角色。
     *
     * @param rids 角色ID数组。
     * @param id   用户ID。
     * @return 受影响的行数。
     */
    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    /**
     * 根据用户ID获取用户信息。
     *
     * @param id 用户ID。
     * @return 用户对象。
     */
    User getUserById(@Param("id") Long id);
}