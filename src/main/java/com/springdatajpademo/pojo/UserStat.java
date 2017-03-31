package com.springdatajpademo.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: lu
 * Date: 17-3-31
 * Time: 上午11:46
 * To change this template use File | Settings | File Templates.
 */
public class UserStat {
    private Long countUser;
    private Long sumHigh;

    public Long getCountUser() {
        return countUser;
    }

    public void setCountUser(Long countUser) {
        this.countUser = countUser;
    }

    public Long getSumHigh() {
        return sumHigh;
    }

    public void setSumHigh(Long sumHigh) {
        this.sumHigh = sumHigh;
    }
}
