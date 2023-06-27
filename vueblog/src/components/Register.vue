<template>
  <el-form :model="loginForm" :rules="rules" class="login-container" label-position="left" label-width="0px"
    v-loading="loading">
    <h3 class="login_title">注册</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="nickname">
      <el-input type="text" v-model="loginForm.nickname" auto-complete="off" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="email">
      <el-input type="text" v-model="loginForm.email" auto-complete="off" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" @click.native.prevent="submitClick" style="width: 100%">注册</el-button>
    </el-form-item>
    <!-- 添加登录链接 -->
    <div class="login-link">
      <span>已有账号？</span>
      <span style="color: #00BFA5;cursor: pointer" @click="login()">点击登录</span>
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
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入有效的邮箱地址', trigger: ['blur', 'change'] }
        ]
      },
      loginForm: {
        username: '',
        nickname: '',
        password: '',
        email: ''
      },
      loading: false
    }
  },
  methods: {
    submitClick: async function () {
      try {
        await this.$refs.loginForm.validate();
        this.loading = true;
        const { status, data } = await postRequest('/register', {
          username: this.loginForm.username,
          nickname: this.loginForm.nickname,
          password: this.loginForm.password,
          email: this.loginForm.email
        });

        this.loading = false;
        if (status === 200) {
          if (data.status === 'activation') {
            this.$router.replace({ path: '/activation' });
          } else {
            this.$alert(data.status, data.msg);
          }
        } else {
          this.$alert('注册失败!', '失败!');
        }
      } catch (err) {
        this.loading = false;
        this.$alert('找不到服务器⊙﹏⊙∥!', '失败!');
      }
    },
    login() {
      this.$router.push('/')
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

.error-message {
  color: red;
}
</style>