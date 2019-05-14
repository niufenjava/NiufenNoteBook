# Vue 安装
## 1. Vue DevTools
在开发 Vue 时，可以在浏览器上安装 Vue DevTools，方便 Vue 调试。[GitHub Vue DevTools](https://github.com/vuejs/vue-devtools#vue-devtools)

## 2. 使用 script 标签引入 Vue
直接下载并使用 script 标签引入，Vue 会被注册为一个全局变量。
> 注：在开发环境下不要使用压缩版本，不然就会失去所有常见错误相关的警告

* 开发版本：https://vuejs.org/js/vue.js （包含完整的警告和调试模式）
* 生产版本：https://vuejs.org/js/vue.min.js （删除了警告，33.30KB min+gzip）

### 2.1 CDN
* 对于制作原型或学习，可以如下方式使用
```html
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
```

* 对于生产环境，需要链接到一个明确的版本号和构建文件，以避免新版本造成的不可预期的破坏
```html
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>
```

## 3. NPM
在用 Vue 构建大型应用时应该用 NPM 安装。NPM 能很好地和 webpack 模块打包器配合使用。同时 Vue 也提供配套公建来开发单文件组件。
```bash
# 最新稳定版
npm install vue
```

## 4. CLI 命令行工具
Vue 提供了官方的 CLI，为单页应用（SPA）快速搭建繁杂的脚手架。只需要几分钟就可以运行起来并带有热重载、保存时 lint 校验，以及生产环境可用的构建版本。

* [GitHub 官方的 CLI](https://github.com/vuejs/vue-cli)
* [Vue CLI 官方文档](https://cli.vuejs.org/)

> CLI 工具假定用户对 Node.js 和相关构建工具由一定程度的了解。最好在熟悉 Vue 本身之后再使用 CLI