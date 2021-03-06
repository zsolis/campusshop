# campusshop-校园 O2O 购物平台
该项目使用Maven管理依赖、打包，可导入Eclipse for JaveEE编辑修改。<br>
全部代码使用UTF-8编码，最低JDK要求1.7。<br>
modeling.mdj是StarUML工程文件，包含用例设计和领域模型。在项目起始阶段设计，在实现过程有改动。<br>
# 项目技术概要
1.项目分为DAO、Service、MVC三层，上层只与其下一层有依赖关系，domain作为各层调用的公共部分，util服务于DAO、Service。各层按相应的包名组织在一起<br>
2.Hibernate作为ORM框架，将domain映射为关系模型，提供DAO服务及事务管理。Jedis提供访问Redis的DAO服务<br>
3.Spring提供实体管理、依赖注入、AOP、MVC服务<br>
4.MVC提供RESTful服务，返回类型为JSON<br>
5.Service类的每个方法都调用DAO，所有Spring AOP对Service类的每个方法包装了事务管理<br>
# 使用前注意事项
1.该项目使用了MySQL数据库、Redis内存存储、七牛云图片云端存储、畅天游手机短信发送等外部服务，在启动项目前需要修改相关配置。<br>
1)main/resources/hibernate.cfg.xml配置MySQL数据库连接信息<br>
2)main/java/com/zsolis/campusshop/util/JedisUtil.java配置Redis链接信息<br>
3)main/java/com/zsolis/campusshop/util/SMSUtil.java配置畅天游账号、密码（http://www.changty.com/）<br>
4)main/java/com/zsolis/campusshop/service/QiniuCloudeHelper.java配置七牛云密钥（http://www.qiniu.com/）<br>
2.数据库由Hibernate根据领域模型映射规则自动生成，初次启动时在MySQL建立gouleme的数据库，同时去掉hibernate.cfg.xml中name="hbm2ddl.auto" update的注释<br>
