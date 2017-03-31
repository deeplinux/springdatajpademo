###Spring Data JPA 动态查询的两种方法
###前言
一般在写业务接口的过程中，很有可能需要实现可以动态组合各种查询条件的接口。如果我们根据一种查询条件组合一个方法的做法来写，那么将会有大量方法存在，繁琐，维护起来相当困难。想要实现动态查询，其实就是要实现拼接SQL语句。无论实现如何复杂，基本都是包括select的**字段**，from或者join的**表**，where或者having的**条件**。在Spring Data JPA有两种方法可以实现查询条件的动态查询，两种方法都用到了Criteria API。
###Criteria API
这套API可用于构建对数据库的查询。
**类型安全**。通过定义元数据模型，在程序编译阶段就可以对类型进行检查，不像SQL需要与Mysql进行交互后才能发现类型问题。
如下即为元数据模型。创建一个元模型类，类名最后一个字符为下划线，内部的成员变量与UserInfo.class这个实体类的属性值相对应。
```Java
@StaticMetamodel(UserInfo.class)
public class UserInfo_ {
    public static volatile SingularAttribute<UserInfo, Integer> userId;
    public static volatile SingularAttribute<UserInfo, String> name;
    public static volatile SingularAttribute<UserInfo, Integer> age;
    public static volatile SingularAttribute<UserInfo, Long> high;
}
```
**可移植**。API并不依赖具体的数据库，可以根据数据库类型的不同生成对应数据库类型的SQL，所以其为可移植的。
**面向对象**。Criteria API是使用的是各种类和对象如CriteriaQuery、Predicate等构建查询，是面向对象的。而如果直接书写SQL则相对于面向的是字符串。

###第一种:通过JPA的Criteria API实现 
1. EntityManager获取CriteriaBuilder
2. CriteriaBuilder创建CriteriaQuery
3. CriteriaQuery指定要查询的表，得到Root<UserInfo>，Root代表要查询的表
4. CriteriaBuilder创建条件Predicate，Predicate相对于SQL的where条件，多个Predicate可以进行与、或操作。
5. 通过EntityManager创建TypedQuery
6. TypedQuery执行查询，返回结果
```Java
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
}
```
###第二种:DAO层接口实现JpaSpecificationExecutor<T>接口
JpaSpecificationExecutor如下，方法参数Specification接口有一个方法toPredicate，返回值正好是Criteria API中的Predicate，而Predicate相对于SQL的where条件。与上一个方法相比，这种写法不需要指定查询的表是哪一张，也不需要自己通过Criteria API实现排序和分页，只需要通过新建Pageable、Sort对象并传参给findAll方法即可，简便一些。
```Java
public interface JpaSpecificationExecutor<T> {
	T findOne(Specification<T> spec);
	List<T> findAll(Specification<T> spec);
	Page<T> findAll(Specification<T> spec, Pageable pageable);
	List<T> findAll(Specification<T> spec, Sort sort);
	long count(Specification<T> spec);
}
```
UserInfoDao实现JpaSpecificationExecutor
```Java
public interface UserInfoDao 
      extends PagingAndSortingRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {}
```
实现Specification
```Java
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
```

###Github代码

###参考资料
[封装JPA(Hibernate)动态查询（CriteriaQuery）](https://www.oschina.net/code/snippet_1864608_37194)
[JPA criteria 查询:类型安全与面向对象](https://my.oschina.net/zhaoqian/blog/133500)
