# ops 项目介绍
分布式session单点登录以及权限统一管理（admin权限系统，system业务系统）

# 收获要点
 本项目结合Spring-boot(目前已升级至2.0正式版)
### 自定义注解开发以及工具类
 
 #### 权限校验
 #### 防重复提交
 #### 参数列表自动赋值
 #### 可控时间长度缓存
 #### 登录校验
 #### 参数列表校验
 #### 统一CRUD增删改查统一日志处理
 #### 异常统一处理
 #### redis存储方式统一管理规范
 #### Mybatis分页开发规范（无侵入嵌入式进行分页封装）
 #### 三级菜单和资源的权限递归查询控制
 #### 新浪开源框架Motan(RPC)结合zookeeper(集群)的应用.
 
 
 ### 重要两个包：
 #### com.yasinyt.admin.base.annotation ( com.yasinyt.system.base.annotation)
 #### com.yasinyt.admin.base.aspect  ( com.yasinyt.system.base.annotation)
 
 提示：由于system业务系统需要admin系统的一些接口应用，只需要运行com.yasinyt.admin.util.ClassUtil类的main方法 自动生成接口jar包到电脑桌面或者对admin系统进行mvn命令运行:mvn package -Dmaven.test.skip=true (也会自动打包到桌面) 然后添加进system项目即可（推荐通过maven私库进行管理最好）。
 
 
 由于公司一起玩项目研究技术的同事离职了，此项目不能再进行愉快的玩耍了。
 于是决定把日常和工作中用到的技术和一些新意的编码方式开放出来（技术一般，希望多多指教）。
 
 # SQL脚本方面
 由于使用的个人服务器，暂不进行开放，如需要项目启动进行调试或提供技术方面的疑问解决，可关注我的github，并进行留言即可。谢谢

 ### 如有兴趣进行共同学习和玩耍技术的可联系我，谢谢
 邮件：tangly@bioodas.com
