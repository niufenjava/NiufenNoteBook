const path = require('path')
// 启用热更新的 第2步
const webpack = require('webpack')
// 导入在内存中生成 HTML 页面的 插件
// 只要是插件，都一定要 放到 plugins 节点中去
// 这个插件的两个作用：
//  1. 自动在内存中根据指定页面生成一个内存的页面
//  2. 自动，把打包好的 bundle.js 追加到页面中去
const htmlWebpackPlugin = require('html-webpack-plugin')

// 这个配置文件，起始就是一个 JS 文件，通过 Node 中的模块操作，向外暴露了一个 配置对象
module.exports = {
  // 大家已经学会了举一反4， 大家觉得，在配置文件中，需要手动指定 入口 和 出口
  entry: path.join(__dirname, './src/main.js'),// 入口，表示，要使用 webpack 打包哪个文件
  output: { // 输出文件相关的配置
    path: path.join(__dirname, './dist'), // 指定 打包好的文件，输出到哪个目录中去
    filename: 'bundle.js' // 这是指定 输出的文件的名称
  },


  // 使用 webpack-dev-server 这个工具，来实现自动打包编译的功能
  // 1. 运行 npm i webpack-dev-server -D 把这个工具安装到项目的本地开发依赖
  // 2. 安装完毕后，这个 工具的用法，和 webpack 命令的用法完全一样
  // 3. 由于，我们是在项目中，本地安装的 webpack-dev-server，所以，无法把它当做 脚本命令，在 powershell 终端中直接运行；（只有那些 安装到 全局 -g 的工具，才能在终端中正常执行）
  // 4. webpack-dev-server 这个工具，如果想要正常运行，要求，在本地项目中，必须按照 webpack
  // 5. webpack-dev-server 帮我们打包生成的 bundle.js 文件，并没有存放到实际的 物理磁盘上，而是直接托管到了电脑的内存中，所以，我们在 项目根目录中，根本找不到这个打包好的 bundle.js
  // 6. 我们可以认为，webpack-dev-server 把打包好的文件，以一种虚拟的形式，托管到了 咱们项目的根目录中，虽然我们看不到它，但是，可以认为，和 dist src node_modules 平级，有一个看不见的文件，叫做 bundle.js
  devServer: { // 这是配置 dev-server 命令参数的第二种形式，相对来说，这种方式麻烦一些
    //  --open --port 3000 --contentBase src --hot
    open: true, // 自动打开浏览器
    port: 3000, // 设置启动时候的运行端口
    contentBase: 'src', // 指定托管的根目录
    hot: true // 启用热更新 的 第1步
  },
  plugins: [ // 配置插件的节点
    new webpack.HotModuleReplacementPlugin(), // new 一个热更新的 模块对象， 这是 启用热更新的第 3 步
    new htmlWebpackPlugin({ // 创建一个 在内存中 生成 HTML  页面的插件
      template: path.join(__dirname, './src/index.html'), // 指定 模板页面，将来会根据指定的页面路径，去生成内存中的 页面
      filename: 'index.html' // 指定生成的页面的名称
    })
  ],
  module: { // 这个节点，用于配置 所有 第三方模块 加载器 
    rules: [ // 所有第三方模块的 匹配规则
      { test: /\.css$/, use: ['style-loader', 'css-loader'] }, //  配置处理 .css 文件的第三方loader 规则
      { test: /\.less$/, use: ['style-loader', 'css-loader', 'less-loader'] }, //配置处理 .less 文件的第三方 loader 规则
      { test: /\.scss$/, use: ['style-loader', 'css-loader', 'sass-loader'] }, // 配置处理 .scss 文件的 第三方 loader 规则
    ]
  }
}

// 当我们在 控制台，直接输入 webpack 命令执行的时候，webpack 做了以下几步：
//  1. 首先，webpack 发现，我们并没有通过命令的形式，给它指定入口和出口
//  2. webpack 就会去 项目的 根目录中，查找一个叫做 `webpack.config.js` 的配置文件
//  3. 当找到配置文件后，webpack 会去解析执行这个 配置文件，当解析执行完配置文件后，就得到了 配置文件中，导出的配置对象
//  4. 当 webpack 拿到 配置对象后，就拿到了 配置对象中，指定的 入口  和 出口，然后进行打包构建；