package com.springdatajpademo;

import com.springdatajpademo.pojo.UserInfo;
import com.springdatajpademo.pojo.UserStat;
import com.springdatajpademo.repository.UserInfoDao;
import com.springdatajpademo.repository.UserInfoDaoSpec;
import com.springdatajpademo.repository.UserInfoExtendDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-30
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */

/**
 * 使用Spring Data Jpa进行动态查询
 *   假设数据库数据为：
 *   id name    age high
 *   1	张三	16	165
 *   2	张三	16	170
 *   3	张三	17	192
 */
public class JPADemoMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JPADemoMain jpaDemoMain = new JPADemoMain();
        jpaDemoMain.testJpa1(applicationContext);
        jpaDemoMain.testJpa2(applicationContext);

    }

    public void testJpa1(ClassPathXmlApplicationContext applicationContext) {
        UserInfoExtendDao userInfoExtendDao = (UserInfoExtendDao)applicationContext.getBean("userInfoExtendDao");

        List<UserInfo> userInfos = userInfoExtendDao.getUserInfo("张三",16,165);
        printUserInfo(userInfos);

        UserStat userStat = userInfoExtendDao.getUserStat("张三");
        System.out.println(userStat.getCountUser());
        System.out.println(userStat.getSumHigh());
    }

    public void testJpa2(ClassPathXmlApplicationContext applicationContext) {
        UserInfoDao userInfoDao = (UserInfoDao)applicationContext.getBean("userInfoDao");
        {
            //三个条件:"张三",16,165
            System.out.println("\n三个条件:张三,16,165");
            List<UserInfo> userInfos = userInfoDao.findAll(UserInfoDaoSpec.getSpec("张三",16,165));
            printUserInfo(userInfos);
        }

        {
            //两个条件:"张三",16,这里假设赋值0为未赋值
            System.out.println("\n两个条件:张三,16");
            List<UserInfo> userInfos = userInfoDao.findAll(UserInfoDaoSpec.getSpec("张三",16,0));
            printUserInfo(userInfos);
        }

        {
            //两个条件:"张三",16,这里假设赋值0为未赋值
            System.out.println("\n一个条件:张三");
            List<UserInfo> userInfos = userInfoDao.findAll(UserInfoDaoSpec.getSpec("张三",0,0));
            printUserInfo(userInfos);
        }

    }

    private void printUserInfo(List<UserInfo> userInfos) {
        if(userInfos!=null) {
            for(UserInfo userInfo : userInfos) {
                System.out.println("userId:"+userInfo.getUserId()+" name:"+userInfo.getName());
            }
        }
    }


}
