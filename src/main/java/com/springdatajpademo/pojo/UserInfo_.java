package com.springdatajpademo.pojo;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-30
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
@StaticMetamodel(UserInfo.class)
 public class UserInfo_ {
    public static volatile SingularAttribute<UserInfo, Integer> userId;
    public static volatile SingularAttribute<UserInfo, String> name;
    public static volatile SingularAttribute<UserInfo, Integer> age;
    public static volatile SingularAttribute<UserInfo, Long> high;
}
