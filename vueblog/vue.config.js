module.exports = {
  transpileDependencies: true,
  lintOnSave: false,
  runtimeCompiler: true,
  devServer: {
    proxy: {
      '/': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        ws: false
      }
    }
  }
}
