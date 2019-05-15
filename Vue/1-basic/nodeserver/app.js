// 导入 http 内置模块
const http = require('http')
// 这个核心模块，能够帮助我们解析 URL 地址，从而拿到 pathname query
const urlModule = require('url')
// 创建一个 http 服务器
const server = http.createServer()
// 监听 http 服务器的request 请求
server.on('request', function(req,res) {
    // write you code here
    // const url = req.url
    const { pathname:url,query} = urlModule.parse(req.url,true)
    console.info(url)
    if(url === '/getScript'){
        // 拼接一个合法的 js 脚本，这里拼接的是一个方法的调用
        // var scriptStr = 'show()'
        var data = {
            name: 'aaa',
            age: 19,
            gender: 'girl'
        }
        var scriptStr = `${query.callback}(${JSON.stringify(data)})`

        // res.end 发送给客户端，客户端把这个字符串，当作js去解析执行
        res.end(scriptStr)
    }else{
        res.end('404')
    }
})

// 指定端口并启动服务器监听
// 启动服务的 nodemon .\app.js
server.listen(3000, function(){
    console.log('server listen at http://127.0.0.1:3000')
})