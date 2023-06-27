<template>
  <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-container" label-position="left"
    label-width="0px" v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="loginForm.rememberMe" label-position="left">记住密码</el-checkbox>
    <el-form-item style="width: 100%">
      <el-button type="primary" @click.native.prevent="submitClick" style="width: 100%">登录</el-button>
    </el-form-item>
    <div class="register-link">
      <span>还没有账号？</span>
      <span style="color: #00BFA5;cursor: pointer" @click="register()">点击注册</span>
    </div>
  </el-form>
</template>


<script>
import { postRequest } from '../utils/api'

export default {
  data() {
    return {
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      checked: true,
      loginForm: {
        username: 'Munich',
        password: '123',
        rememberMe: false
      },
      loading: false
    }
  },
  methods: {
    submitClick: async function () {
      try {
        await this.$refs.loginFormRef.validate();
        this.loading = true;
        const { status, data } = await postRequest('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password,
          'remember-me': this.loginForm.rememberMe
        });

        this.loading = false;
        if (status === 200) {
          if (data.status === 'success') {
            this.$router.replace({ path: '/home' });
          } else {
            this.$alert('登录失败!', '失败!');
          }
        } else {
          this.$alert('登录失败!', '失败!');
        }
      } catch (err) {
        console.error(err);
      }
    },
    register() {
      this.$router.push('/reg')
    }
  }
}
</script>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.login_remember {
  margin: 0px 0px 35px 0px;
  text-align: left;
}
</style>