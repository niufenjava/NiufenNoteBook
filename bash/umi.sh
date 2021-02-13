# https://umijs.org/zh-CN/docs/getting-started
#先找个地方建个空目录。
mkdir umidemo && cd umidemo
#通过官方工具创建项目，
yarn create @umijs/umi-app
#安装依赖
yarn
#启动项目
yarn start
#部署发布
## 构建
yarn build
## 构建产物默认生成到 ./dist 下，然后通过 tree 命令查看，
tree ./dist
# 本地验证
## 发布之前，可以通过 serve 做本地验证，
yarn global add serve
serve ./dist
