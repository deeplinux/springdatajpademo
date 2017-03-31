package com.springdatajpademo.repository;

import com.springdatajpademo.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-30
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {

}
