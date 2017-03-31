package com.springdatajpademo.pojo;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-30
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="obj_user")
@NamedQuery(name="UserInfo.findAll", query="SELECT o FROM UserInfo o")
public class UserInfo {
    private int userId;
    private String name;
    private int age;
    private long high;

    @javax.persistence.Id
    @Column(name="user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }
}
