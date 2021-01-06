<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'2'"></v-navbar>
    </el-header>
    <el-main>
      <el-row>
        <el-col :offset="1" :span="6">
          <el-form ref="myPass" :rules="pass_rule" :model="myPass" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="myPass.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="旧密码">
              <el-input type="password" v-model="myPass.oldPassword" ></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input type="password" v-model="myPass.newPassword" ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitPass('myPass')">提交修改</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
    export default {
        name: "ChangePass",
      data(){
          return{
            pass_rule:{
              oldPassword:[{required:true,message:'请输入旧密码',trigger:'blur'}],
              newPassword:[{required:true,message:'请输入新密码',trigger:'blur'}],
            },
            myPass:{
              username:this.$store.state.username,
              oldPassword:'',
              newPassword:'',
            },

          }
      },methods:{
        submitPass(formName) {
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$axios.post('/user/modifyPass',{
              username: this.myPass.username,
                oldPassword:this.myPass.oldPassword,
                newPassword:this.myPass.newPassword
              }).then(resp => {
                if (resp.data === 'success') {
                  alert('修改成功！')
                } else if(resp.data === 'wrong password'){
                  alert('密码错误！')
                }
              })
                .catch(error => {
                  console.log(error);
                  alert('网络连接失败')
                })
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
