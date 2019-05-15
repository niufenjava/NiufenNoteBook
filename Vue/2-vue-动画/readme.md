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
<transition
  enter-active-class="bounceIn"
  leave-active-class="bounceOut"
  :duration="400"
>
  <h3 v-if="flag" class="animated">{{ msg }}</h3>
</transition>
```

## 钩子函数实现半场动画

> 只有进入，没有退出的动画。例如，加入购物车。

```html
<transition
  v-on:before-enter="beforeEnter"
  v-on:enter="enter"
  v-on:after-enter="afterEnter"
  v-on:enter-cancelled="enterCancelled"
  v-on:before-leave="beforeLeave"
  v-on:leave="leave"
  v-on:after-leave="afterLeave"
  v-on:leave-cancelled="leaveCancelled"
>
  <!-- ... -->
</transition>
```

### 使用构造函数，模拟小球半场动画

```css
.ball {
  width: 15px;
  height: 15px;
  border-radius: 50%;
  background-color: red;
}
```

```html
<!-- 有个按钮点击的时候进行半场动画 -->
<transition
  @before-enter="beforeEnter"
  @enter="enter"
  @after-enter="afterEnter"
>
  <div class="ball" v-show="flag"></div>
</transition>
```

```js
methods: {
  // 注意：动画钩子函数的第一个参数：el，表示 要执行动画的那个 DOM 元素，是个元素的 JS DOM 对象
  // 可以认为，el 时通过 document.getElementById('') 这种方式获得的原生对象
  beforeEnter(el){
    // beforeEnter 表示动画如此之前，此时，动画尚未开始。可以在 beforeEnter 中，设置元素开始动画之前的起始样式
    // 设置小球开始动画之前，小球在 0,0 这个坐标
    el.style.transform = "translate(0, 0)"
  },
  enter(el, done){
    // enter 表示动画开始之后的样式，这里，可以设置小球完成动画 之后的结束状态
    el.offsetWidth // 这句话没有实际的作用，但是如果不写，出不来动画效果。可以认为，el.offsetWidth 会强制动画刷新
    el.style.transform = "translate(150px,450px)"  // transform 位移
    el.style.transition = 'all 2s ease' //过度动画
    // 这里的 done 就是 afterEnter 函数，也就是说，done 是 afterEnter 函数的引用
    done() //当动画完成之后，需要显式的调用 done 函数
  },
  afterEnter(el){
    console.log('afterEnter')
    // afterEnter 是动画完成之后会调用 afterEnter
    this.flag = !this.flag
  },
}
```

## 列表动画

实现：添加一个对象到表格中。添加的时候要有动画效果。
使用 .v-move 和 .v-leave-active 可以实现动画的渐变移除
给 transition-group 添加 appear 属性 实现页面刚展示出来的时候入场效果
通过为 transition-group 元素，设置 tag 属性，指定 transition-group 渲染为指定的元素，如果不知道 tag 属性，默认为 span 属性
2. 展示

```html
<!-- 这里面来个添加 input  -->
<ul>
  <!-- 在实现列表过度的时候，如果需要过度的元素，是通过 v-for 循环渲染出来的，不能使用 transition 包裹。需要使用 transitionGroup -->
  <!-- 如果要为 v-for 循环创建的元素设置动画，必须为每一个元素 设置 :key 属性 -->
  <transitionGroup>
    <li v-for="item in list" :key="item.id">
      {{item.id}} --{{item.name}}
    </li>
  </transitionGroup>
</ul>
```

3. 样式

```css
li {
  border: 1px dashed #999;
  margin: 5px;
  line-height: 35px;
  padding-left: 5px;
  font-size: 12px;
}

li:hover{
  background-color:hotpink;
  transition: all 0.4s ease;
}

.v-enter,
.v-leave-to {
  opacity: 0;
  transform: translateY(80px);
}

.v-enter-active,
.v-leave-active {
  transition: all, 0.6s, ease;
}
```
