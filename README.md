# my-springboot-starter
基于Springboot自动配置原理实现的一个crud starter，开箱即用。在Springboot项目中，用maven或者其他方式引入my-springboot-starter的jar包，立即拥有一套基于SpringSecurity+Jwt实现的一套权限管理系统（待完善）、统一的响应格式和异常处理、一些常用的工具类...

## 初始配置
1. 将my-springboot-starter的jar包安装到本地仓库：
   ``` 
   mvn clean install -DSkiptests
   ```
2. 在新项目中引入jar包（注意jar包冲突，my-springboot-starter依赖了一些常用jar包）：
    ```xml
    <dependency>
        <groupId>com.hsccc</groupId>
        <artifactId>my-springboot-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```
3. 目前需要手动在数据库创建表，建表语句见init.sql文件，用的是mysql数据库。
4. 在新项目的配置文件中引入数据库配置：
   ```yaml
   spring:
    datasource:
      url: jdbc:mysql://ip:端口号/test_db?characterEncoding=utf-8&serverTimezone=UTC
      username: 用户名
      password: 密码
      driver-class-name: com.mysql.cj.jdbc.Driver
   ```
然后就可以开始使用啦。

## 功能简介

### 权限管理 
权限管理系统参考了[RuoYi-Vue](http://doc.ruoyi.vip/ruoyi-vue/) ，只开发了一点点，目前只提供了以下接口：
- 登录（获取Token）
   ```
   POST '127.0.0.1:8080/auth/token' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "userName": "hsccc",
       "password": "123456"
   }'
   ```
- 注销
   ```
   POST '127.0.0.1:8080/auth/logout' \
   --header 'Authorization: Bearer Token字符串'
  ```
- 查询用户信息
   ```
   GET '127.0.0.1:8080/user/{user_id}' \
   --header 'Authorization: Bearer Token字符串'
   ```
### 统一的响应格式
所有的接口都会自动被封装一层：
```json
{
    "path": "/auth/token",
    "code": 200,
    "message": "操作成功",
    "timestamp": 1648808073583,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwZDZkZmFjNC02YWRiLTQyZWQtOGQ0ZS1hZDU2ZTQ3YWUzOGEifQ.axWqVxy6Qf6VJ2JEyNG87hSvHHfzzTF7tAWZE45xdHI"
    }
}
```
### 自定义异常
简化了定义异常的冗余代码：
```java
public class UtilException extends ApiException {
    UtilException(ApiExceptionBuilder<?> builder) {
        super(builder);
    }

    public static class Builder extends ApiExceptionBuilder<UtilException> {
        public Builder(String message) {
            super(UtilException.class);
            message(message);
            status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
```
使用：
```java
throw new UtilException.Builder(e.getMessage()).build();
throw new OtherException.Builder().message(message).errorData(data).throwable(e).status(HttpStatus.FORBIDDEN).build();
```

### BeanUtil
在spring的BeanUtils上封装了一层，用法如下：
```java
// 1. 一行代码搞定Entity、DTO转换
TargetClass targetObject = BeanUtil.convert(sourceObject, TargetClass.class);

// 2. 更新对象属性值（sourceObject的null值不覆盖targetObject）
BeanUtil.copyProperties(sourceObject, targetObject);

// 3. 批量Entity、DTO对象转换
List<TargetClass> targetObjects = BeanUtil.convertInBatch(sourceObjectList, TargetClass.class);
```

## 自定义配置
- myspringbootstarter.autoconfigure.response=true 配置是否开启统一响应值功能
- myspringbootstarter.autoconfigure.exception=true 配置是否开启全局异常处理功能
- myspringbootstarter.autoconfigure.security=true 配置是否使用权限管理功能，设置为false将会使用SpringSecurity的默认权限管理

#### 如何关闭权限管理功能？
1. myspringbootstarter.autoconfigure.security设置为false，仅设置此项为false将会使用SpringSecurity的默认权限管理。
2. 再增加配置spring.autoconfigure.exclude: org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration就可以关闭了。

#### 如何重写SpringSecurity相关的权限配置？  
自定义一个WebSecurityConfigurerAdapter或者SecurityConfig的子类。（SecurityConfig是my-springboot-starter中的WebSecurityConfigurerAdapter的一个实现类）。  

#### 如何修改统一响应的格式？
重新定义一个ResponseBodyAdvice，my-springboot-starter中的ApiResponseAdvice就会失效。  

#### 如何替换Token的缓存实现？
目前ICache的实现类只有以内存作为缓存的实现类，所以只在单实例情况下Token相关功能才有效。
自定义一个实现ICache接口的类，放入Spring IoC容器，my-springboot-starter中的ICache实现类就会失效。  

