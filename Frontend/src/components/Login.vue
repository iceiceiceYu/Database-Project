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
            this.$axios.post('/user/login', {
              username: this.loginForm.username,
              password: this.loginForm.password
            })
              .then(resp => {
                console.log(resp.data)
                if (resp.status === 200 && resp.data!=='') {
                  this.$store.commit('login', resp.data);
                  this.$router.replace({path: '/dashboard'})
                  this.$notify({
                    title: '登录成功',
                    type: 'success'
                  });
                } else {
                  alert('登陆失败！请检查用户名或密码。')
                }
              })
              .catch(error => {
                console.log(error);
                alert('网络连接失败')
              })
          } else {
            console.log("错误提交！")
            return false
          }
        })
      }
    }
  }
</script>
