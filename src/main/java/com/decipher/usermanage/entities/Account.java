package com.decipher.usermanage.entities;

import com.decipher.usermanage.enums.Role;

import javax.persistence.*;

/**
 * Created by decipher16 on 3/3/17.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Integer id;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL",unique = true)
    private String email;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "ROLE", nullable = false, length = 45)
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Column(name = "PERMANENT_ADDRESS")
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
