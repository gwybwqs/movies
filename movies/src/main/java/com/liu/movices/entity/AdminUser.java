package com.liu.movices.entity;

import javax.persistence.*;

@Entity
@Table(name = "adminUser")
public class AdminUser {
    @Id
    @Column(name = "au_id", nullable = false, length = 11)
    private String id;
    /**用户名*/
    @Column(name = "au_username", nullable = false, length = 64)
    private String name;
    /**密码*/
    @Column(name = "au_password", nullable = false, length = 64)
    private String password;
    /**角色*/
    @Column(name = "au_role", nullable = false, length = 14)
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
