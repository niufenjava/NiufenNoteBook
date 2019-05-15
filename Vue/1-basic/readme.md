
# Day2
## 过滤器
概念：Vue 允许自定义过滤器，可被用作一些常见的文本格式化。
使用位置：1、mustache 插值表达式{{}} 和 v-bind 表达式。过滤器应该被添加在 JavaScript 表达式的尾部，由“管道”夫表示。

### 私有过滤器
```js
// 过滤器的定义语法 
Vue.filter('过滤器的名称', function(){})
// 过滤器中的 function，第一个参数，已经被规定死了，
// 永远都是 过滤器管道符前面传递过来的数据
Vue.filter('过滤器名称', function (data) {
    return data + '123'
})
```
### 按键修饰符
Vue 内置了几个，比如enter，如果不够用。可以自定义。



### 自定义全局指令修饰符


目标：让 输入框 自动获取焦点。只要加上 v-focus 这个指令，就自动获取焦点。
实现：
1. 使用 Vue.directive() 定义全局的指令。其中。
1.1 参数1：指令的名称，注意，在定义的时候，指令的名称前面，不需要加 v- 前缀。但是在调用的时候必须在指令名称前加上 v- 前缀来进行调用。
1.2 参数2：是一个对象，这个对象身上，有一些指令相关的函数，这些函数可以在特定的阶段，执行相关的操作
```js
Vue.directive('focus',{
    // 每当指令第一次绑定到元素时调用。只执行一次，可用于初始化。
    // 注意：bind 的时候，还没有加入到元素中去
    // 在元素 刚绑定了指令的时候，还没有插入到 DOM 中去，这时候 ，调用 focus 方法没有作作用，因为一个元素只有插入 DOM 之后，才能获取焦点。
    bind:function(el){ 
        // 注意，在每个函数中，第一个参数，永远是 el，表示被绑定了指令的那个元素。这个 el 参数，是一个原生的 JS对象（DOM对象）。
        // el.focus()
    },
    // 表示元素插入父节点时调用
    // * 和 JS 行为有关的操作，最好在 inserted 中操作，防止 行为失效
    inserted:function(el){
        el.focus()
    },
    // 当 VNode 更新的时候，或执行 update，可能会触发多次
    updated:function(){

    }

})
```

* 自定义指令，input 输入值，就改变颜色
```js

Vue.directive('color',{
    // 自定义一个 设置字体颜色的 指令
    // 样式相关的操作一般都在 bind 里面执行
    bind: function(el, binding){
        // 生效的原因，样式只要交给 DOM 就行了，DOM 加载完后就会渲染。这个跟事件不一样。
        // * 样式，只要通过指令绑定给了元素，不管这个元素有没有被插入到页面中去，这个元素肯定有了一个内定的样式
        // * 将来元素肯定会显示到页面中去，这时候，浏览器的渲染引擎必然会解析样式，应用给这个元素
        el.style.color = binding.value
    }

    // 使用
    // v-color="'blue'"
})
```
* 定义私有指令
```js
// 自定义私有指令，某个 Vue 实例对象私有，与 methods 同一级别
directives:{
    'fontWeight':{
        bind: function(el,binding){
            el.style.fontWeight = binding.value
        }
    }
}
```
#### 自定义指令简写
```js
// 注意，这个 function 等同于把 代码鞋到了 bind 和 update 中去
Vue.directive('bgColor', function(el, binding{
    el.style.backgroundColor = binding.value
}))
```

注意：
1. 在 Vue 中所有的指令，在调用的时候，都以 v- 开头

## Vue 实例的生命周期
#### 组件创建期间的4个钩子函数

+ 什么是生命周期：从 Vue 实例创建、运行、到销毁期间，总是伴随着各种各样的事件，这些事件，统称为生命周期
+ 生命周期钩子：就是生命周期事件的别名而已 = 生命周期函数 = 生命周期事件
+ 主要的生命周期函数分类：
    - 创建期间的生命周期函数：
        + beforeCreate：实例刚刚在内存中北创建出来，此时，还没有初始化好 data 和 methods 属性
        + created：实例已经在内存中创建OK，此时 data 和 methods 已经创建 OK，此时还没有开始编译模板
        + beforeMonth：此时已经完成了模板的编译，但是还没有挂载到页面中
        + mounted：此时，已经将编译好的模拟，挂载到页面指定的容器中显示
    - 运行期间的生命周期函数：
        + beforeUpdate：状态更新之前执行此函数，此时 data 中的状态值是最新的，但是界面上显示的数据还是旧的，因为此时还没有开始重新渲染 DOM 节点
        + updated：实例更新完毕之后调用此函数，此时 data 中的状态值 和 界面上显示的数据，都已经完成了更新，界面已经被重新渲染好了。
    - 销毁期间的生命周期函数：
        + beforeDestroy：实例销毁之前调用。在这一步，实例仍然完全可用。
        + destroyed: 实例销毁之后调用。调用后，Vue 实例指示的所有东西都会解绑定，所有的事件监听都会被移除，所有的子实例也会被销毁。

## vue-resource 实现 get、post、jsonp请求
* github：https://github.com/pagekit/vue-resource
> 除了 vue-resource 之外，还可以使用 `axios` 的第三方包实现数据的请求
1. 之前的学习中，如何发起数据请求？
2. 场景的数据请求类型？ get、post、jsonp
3. 测试的 URL 请求资源地址：
 + GET 请求地址：http://vue.studyit.io/api/getlunbo
 + POST 请求地址：http://vue.studyit.io/api/post
 + jsonp 请求地址：http://vue.studyit.io/api/jsonp
4. JSONP 的实现原理
 + 由于浏览器的安全性限制，不允许 AJAX 跨域访问 协议不同、域名不同、端口号不同的 数据接口，浏览器认为这种访问不安全；
 + 可以通过动态创建 script 标签的形式，把 script 标签的 scr 属性，指向数据接口的地址，因为 script 标签不存在跨域限制，这种数据获取方式，称作 JSONP（注意：根据 JSONP 的实现原理，知晓，JSONP 只支持 Get 请求）；
 + 具体实现过程：
  - 先在客户端定义一个回调方法， 预定义对数据的操作；
  - 再把这个回调方法的名称，通过 URL 传参的形式，提交到服务器的数据接口
  - 服务器数据接口组织好要发送给客户端的数据，再拿着客户端传递过来的回调方法名称，拼接处一个调用这个方法的字符串，发送给客户端去解析执行；
  - 客户端拿到服务器返回的字符串之后，当作 Script 脚本去解析执行，这样就能够拿到 JSONP 的数据了。
+ 带大家通过 Node.js，来手动实现一个 JSONP 的请求例子

## 根据vue-resource 实现一个品牌管理的增删改查

+ vue-resource全局配置数据接口的根域名
 - 如果我们通过全局配置了，请求的数据接口 根域名，则，在每次单独发起 http 请求的时候，请求的 url 路径，应该以相对路径开头，前面不能带 / ，否则不会启用根据经做拼接
```js
vue.http.option.root = '/root'
```

+ vue-resource全局配置 emulateJSON:true
```js
vue.http.options.emulateJSON = true
```