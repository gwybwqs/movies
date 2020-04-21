package com.liu.movices.dao;


import com.liu.movices.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminUserDao extends JpaRepository<AdminUser,String>  {
    AdminUser findByIdAndAndName(String id,String name);
    /**根据账号与密码查询*/
    AdminUser findByIdAndAndPassword(String id,String password);

    /**根据用户角色分页查询*/
    Page<AdminUser> findByRole(String role, Pageable pageable);

    /**根基账号或用户名分页查询*/
    Page<AdminUser> findByIdLikeOrNameLikeAndAndRole(String id ,String name, String role, Pageable pageable);

}
