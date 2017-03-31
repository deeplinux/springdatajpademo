package com.springdatajpademo.repository;

import com.springdatajpademo.pojo.UserInfo;
import com.springdatajpademo.pojo.UserInfo_;
import com.springdatajpademo.pojo.UserStat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-31
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserInfoExtendDao {

    @PersistenceContext(unitName = "springJpa")
    EntityManager em;

    public List<UserInfo> getUserInfo(String name,int age,int high) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query = cb.createQuery(UserInfo.class);

        //from
        Root<UserInfo> root = query.from(UserInfo.class);

        //where
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
        query.where(p1);

        List<UserInfo> userInfos = em.createQuery(query).getResultList();
        return userInfos;
    }



    public UserStat getUserStat(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();

        //from
        Root<UserInfo> root = query.from(UserInfo.class);

        //select
        Selection<Long> countUser;
        Selection<Long> sumHigh;

        countUser = cb.count(root.get(UserInfo_.userId));
        sumHigh = cb.sum(root.get(UserInfo_.high));

        CompoundSelection<Tuple> selection = cb.tuple(countUser, sumHigh);

        //where
        Predicate predicate = cb.equal(root.get(UserInfo_.name), name);

        //query
        CriteriaQuery<Tuple> criteriaQuery = query.select(selection).where(predicate);

        Tuple tuple = em.createQuery(criteriaQuery).getSingleResult();
        UserStat userStat = new UserStat();
        userStat.setCountUser(tuple.get(countUser));
        userStat.setSumHigh(tuple.get(sumHigh));

        return userStat;
    }
}
