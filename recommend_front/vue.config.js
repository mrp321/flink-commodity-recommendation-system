// vue.config.js
var host_local = "172.31.134.148";
var host_remote = "192.168.229.139";
var host = 'http://' + host_local + ':8090';
module.exports = {
    // 基本路径
    publicPath: '.',
    devServer: {
        host: '0.0.0.0',
        port: 8088,
        proxy: {
            '/business': {
                target: host,
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/business': '/'
                }
            }
        },
        disableHostCheck: true,
    }
}