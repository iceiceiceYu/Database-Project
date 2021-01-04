<template>
  <div>
    <h1>{{msg}}</h1>

    <div class="line"></div>
    <el-row :gutter="20">
      <el-col :span="8" :offset="8">
        <el-form class="login-container" :model="loginForm" ref="loginForm"
                 :rules="rules" label-width="80px">
          <el-form-item label="用户名：">
            <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码：">
            <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="login-button" type="primary" @click="submitClick('loginForm')">登录</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  export default {
    name: 'Login',
    data() {
      return {
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        loginForm:{
          username:'',
          password:''
        },
        checked: true,
        msg: 'Please Login'
      }
    },
    methods: {
      submitClick(formName){
        this.$refs[formName].validate(valid => {
          if(valid) {
            this.$axios.post('/login', {
              username: this.loginForm.username,
              password: this.loginForm.password
            })
              .then(resp => {
                if (resp.status === 200 && resp.data.hasOwnProperty("token")) {
                  alert('登录成功！');
                  this.$store.commit('login', resp.data);
                  this.$router.replace({path: '/dashboard'})
                } else {
                  alert('login error')
                }
              })
              .catch(error => {
                console.log(error);
                alert('login error')
              })
          } else {
            console.log("error submit！")
            return false
          }
        })
      }
    }
  }
</script>
