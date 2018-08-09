# Darts

基于 <code>Java8</code> 创造的轻量级、高性能、简洁优雅的Web框架 😋

## 简介
darts是一个轻量、优雅的MVC框架，提供了类似于<code>Spring</code>的Bean容器、IOC、AOP、MVC等功能。

## IOC

## AOP

## 增强AOP

## MVC

### Controller分发器ControllerHandler
功能流程：
1. 在构造方法中获取Bean容器`BeanContainer`的单例实例
2. 获取并遍历`BeanContainer`中存放的被RequestMapping注解标记的类
3. 遍历这个类中的方法，找出被`RequestMapping`注解标记的方法
4. 获取这个方法的参数名字和参数类型，生成`ControllerInfo`（存储Controller相关信息的封装类）
5. 根据`RequestMapping`里的`value()`和`method()`生成`PathInfo`(存储http相关信息的封装类)
6. 将生成的`PathInfo`（key）和`ControllerInfo`（value）存到`pathControllerMap`中
7. 其他类通过调用`getController()`方法获取到对应的controller

### Controller结果执行器ResultRender
功能流程：
1. 调用`getRequestParams()`获取HttpServletRequest参数（目前只获取get或post的参数）
2. 调用`instantiateMethodArgs()`实例化调用方法要传入的参数（实例化只支持java的原生数据类型，还没实现自定义类的实例化）
3. 通过反射调用目标controller的目标方法
4. 调用`resultResolver()`解析方法的返回值，选择返回页面或者json

## 效果图
### 列表页
访问 http://localhost:8888/user/list 可进入列表页：

![](https://ws1.sinaimg.cn/large/73d640f7ly1fu3x6wtjd4j20jc0gmmxy.jpg)

后台日志如下：

![](https://ws1.sinaimg.cn/large/73d640f7ly1fu3xhuatpej21ni04kwgj.jpg)


访问 http://localhost:8888/user/detail?id=1 可进入id为1的详情页

![](https://ws1.sinaimg.cn/large/73d640f7ly1fu3xae05w6j20mu09kaad.jpg)

后台日志如下：

![](https://ws1.sinaimg.cn/large/73d640f7ly1fu3xir1vqyj21ls04gdhs.jpg)