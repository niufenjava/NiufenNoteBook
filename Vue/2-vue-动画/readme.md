# Vue 中的动画

> 为什么要有动画：动画能够提高用户的体验，帮助用户更好的理解页面中的功能
> [官方文档](https://cn.vuejs.org/v2/guide/transitions.html)

### 需求 1 点击按钮，让 h3 显示，再点击，让 h3 隐藏

1. 创建一个 h3
2. 创建一个 button，toggle
3. 使用 Vue 过度类名，实现动画
   1. 使用 transition 元素，把需要被动画控制的元素包裹起来。
   2. 自动以两组样式，来控制 transition 内部元素实现动画

```CSS
/* v-enter 【这是一个时间点】是进入之前，元素的起始状态，此时还没有开始进入 */
/* v-leave-to 【这是一个时间点】是动画离开之后，离开的终止状态，此时，元素 动画已经结束了 */
.v-enter,
.v-leave-to{
    opacity:0;
    /* 设置元素起始位置 */
    transform: translateX(180px)
}
/* v-enter-active 【入场动画的时间段】 */
/* v-leave-active 【离场动画的时间段】 */
.v-enter-active,
.v-leave-active{
    /* 哪些属性过度，花费多次实践，怎么动 */
    transition:all 0.8s ease;
}
```

```html
<div id="app">
  <input type="button" value="动起来" @click="flag=!flag" />
  <transition name="" mode="">
    <h3 v-if="flag">{{ msg }}</h3>
  </transition>
</div>
```

### 动画-修改 v-前缀

transition 的 name 属性，用于区分不同组的动画。在 style 中 使用 my- 替代 v-

```css
.my-enter,
.my-leave-to {
  opacity: 0;
  transform: translateX(180px);
}
.my-enter-active,
.my-leave-active {
  transition: all 0.8s ease;
}
```

```html
<transition name="my" mode="">
  <h3 v-if="flag">{{ msg }}</h3>
</transition>
```

> transition 默认去找 v-开头的动画样式，如果不想用 v，并且有多种不同的，可以使用 transition 的 name 属性

### 使用第三方 animate.css 实现动画

> [GitHub](https://github.com/daneden/animate.css) > [官网](https://daneden.github.io/animate.css/)

```css
/* 入场使用 bounceIn 离场使用 bounceOut */
```

```js
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.0/animate.min.css">
</head>
```

```html
<!-- 添加入场、离场两个类就可以了。 -->
<transition
  enter-active-class="animated bounceIn"
  leave-active-class="animated bounceOut"
>
  <h3 v-if="flag">{{ msg }}</h3>
</transition>
<!-- 方法2 -->
<!-- 使用:duration="400" 来统一设置 入场 和 离场时候的动画时长 -->
<!-- 也可以使用 :duration="enter:200,leave:400" 的方式分别设置 入场 和 离场的时长 -->
<transition enter-active-class="bounceIn" leave-active-class="bounceOut" :duration="400">
  <h3 v-if="flag" class="animated">{{ msg }}</h3>
</transition>
```
