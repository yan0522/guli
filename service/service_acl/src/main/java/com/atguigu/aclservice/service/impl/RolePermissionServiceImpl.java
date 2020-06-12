package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.RolePermission;
import com.atguigu.aclservice.mapper.RolePermissionMapper;
import com.atguigu.aclservice.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    //分配权限
    @Override
    public void saveOrUpdatePermission(List<RolePermission> rolePermissionList) {

        //v1---如下写法有个问题：如果某个角色减去了一些权限，但是数据库里还是会有那些权限
        /*for (RolePermission rolePermission : rolePermissionList) {
            String permissionId = rolePermission.getPermissionId();
            String roleId = rolePermission.getRoleId();
            QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",roleId);
            wrapper.eq("permission_id",permissionId);
            Integer integer = baseMapper.selectCount(wrapper);
            if (integer <= 0) {
                baseMapper.insert(rolePermission);
            }
        }*/

        //v2---先删除数据库中相应角色的全部权限，然后再添加
        String roleId = rolePermissionList.get(0).getRoleId();
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        baseMapper.delete(wrapper);
        for (RolePermission rolePermission : rolePermissionList) {
            baseMapper.insert(rolePermission);
        }

    }
}
