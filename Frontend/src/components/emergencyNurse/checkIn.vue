<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-container>
      <el-aside>
        <v-side :activeIndex="'2'"></v-side>
      </el-aside>
      <el-main>
        <h1>登记入院信息</h1>
        <el-form :model="newPatient" ref="newPatient"
                 :rules="rules" label-width="80px">
          <el-form-item label="病人姓名">
            <el-input type="text" v-model="newPatient.name"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="newPatient.gender" label="male">男</el-radio>
            <el-radio v-model="newPatient.gender" label="female">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input type="text" v-model="newPatient.age"></el-input>
          </el-form-item>
          <el-form-item label="病情等级">
            <el-radio-group v-model="newPatient.level" size="small">
              <el-radio-button label="mild">轻症</el-radio-button>
              <el-radio-button label="severe">重症</el-radio-button>
              <el-radio-button label="critical">危重症</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitClick('newPatient')">提交</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "CheckIn",
    data() {
      return {
        rules: {
          name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
          age: [{required: true, message: '请输入年龄', trigger: 'blur'}]
        },
        newPatient: {
          name: '',
          gender: 'male',
          age:'',
          level: 'mild'
        }
      }
    }, methods: {
      submitClick(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.$axios.post('/emergencyNurse/newPatient', {
              name: this.newPatient.name,
              gender: this.newPatient.gender,
              age: this.newPatient.age,
              level: this.newPatient.level
            }).then(resp => {
              alert('修改成功')
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
