
module.exports = {
    entry: ["babel-polyfill", './index.js', 'webpack-dev-server/client?http://0.0.0.0:7777',],
    module: {
        rules: [{
            test: /\.(js|jsx)/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader",
                options: {
                    presets: ["react", "latest"]
                }
            }
        },
            {
                test: /\.(scss|sass|css)/,
                use: [{
                    loader: "style-loader"
                }, {
                    loader: "css-loader"
                }, {
                    loader: "sass-loader"
                }]
            }]
    },
    output: {
        path: __dirname + "/build",
        filename: "bundle.js"
    },
    devServer: {
        overlay: true
    }
};