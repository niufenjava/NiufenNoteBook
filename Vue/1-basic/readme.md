
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

1. HTML 元素