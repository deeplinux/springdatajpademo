package com.springdatajpademo.repository;

import com.springdatajpademo.pojo.UserInfo;
import com.springdatajpademo.pojo.UserInfo_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-30
 * Time: 下午8:05
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoDaoSpec {
    public static Specification<UserInfo> getSpec(final String name,final int age,final int high) {
        return new Specification<UserInfo>() {

            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = null;
                if(name!=null) {
                    Predicate p2 = cb.equal(root.get(UserInfo_.name),name);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                if(age!=0) {
                    Predicate p2 = cb.equal(root.get(UserInfo_.age), age);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                if(high!=0) {
                    Predicate p2 = cb.equal(root.get(UserInfo_.high), high);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                return p1;
            }
        };
    }
}
