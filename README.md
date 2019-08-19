## 启动介绍
1. 需要nginx 配置图片位置
2. 启动redis 缓存code 和 openId
3. 小程序生成海报需要设计下图片


## 开发手册
1. 添加选项卡
```$xslt
//前提。在main.js中声明全局变量
var tab;

//因为iframe 获取父容器的变量
var t =  {url: "/article/showArticle", icon: "", title: "文章管理", id: "966"}
parent.tab.tabAdd(t)
```