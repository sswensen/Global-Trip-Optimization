
module.exports = {
    entry: './index.js',
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
    }
};