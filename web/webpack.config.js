const path = require('path');

module.exports = {
    entry: './build/kotlin-js-min/main/web.js',
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'build/webpack')
    },
    mode: 'development',
    devtool: 'eval-source-map'
};