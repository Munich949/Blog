package com.dlnu.mapper;

import com.dlnu.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 这个接口表示 Role 实体的 Mapper。
 * 它负责在数据库中执行对 Role 表的 CRUD 操作。
 */
@Mapper
public interface RolesMapper {
    /**
     * 向用户添加角色。
     *
     * @param roles 角色数组。
     * @param uid   用户ID。
     * @return 添加的角色数量。
     */
    int insertRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    /**
     * 获取所有角色列表。
     *
     * @return 角色列表。
     */
    List<Role> selectAllRole();

    /**
     * 根据用户ID获取角色列表。
     *
     * @param uid 用户ID。
     * @return 与用户关联的角色列表。
     */
    List<Role> selectRolesByUid(Long uid);
}