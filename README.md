# 项目名称：交通信号接口
 ### 项目描述
 1.	规范化信号监测评价优化系统对第三方系统接口的要求
 2.	适应NEMA标准的基于信号灯组环控制方式
 3.	适应国内传统的基于信号状态控制方式
 
 ###项目访问地址
  http://localhost:8080/utcsystem/swagger-ui.html
  
  基础表t_base_vendor_method 维护厂商外部接口url
 
 ### 开发规范
 1. 严格按照编码规范进行代码编写
 2. 严格按照目录结构进行分类
 3. 严禁使用魔鬼数字
 4. 类方法必须添加注释说明
 5. TODO标识进行中因为某原因停滞
 
 ### 注释模板
 1.类注释
 
     /**
      * @Description: [类功能说明]
      * @author yinguijin
      * @version 1.0
      * Created on 创建日期
      */
 2.方法注释
 
    /**
     * @description: 方法功能说明
     * @param 请求参数
     * @return 返回结果
     * @author yinguijin
     * @date 创建日期
     */
 
 ### 目录描述    
    src下为源码  
    jar下为依赖jar包 
    classes下为源码编译后的jar包
    test下为测试用例
    common 公共包
        annotation 自定义注解
        component 公共类
        constant 常量类
        enums 枚举类
        exception 自定义异常
        util 工具类
    config 配置
        init 初始化加载项
     task 定时任务
    
 ### 项目配置
    # 信号优化
    signalamp:
      # 供应商-厂家接口
      vendor:
        qs:
          # QS接口适配服务是否开启 false时关闭青松适配接口
          switch: true
          # QS接口前缀
          rootpath: http://192.168.205.210/scss.api
          # 用户名密码
          username: ****
          password: ****
          # 登录地址 获取token
          login: /api/user/login
      # 基础信息
      basic:
        # 平均车长
        carLength: 7.0
        