<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'2'"></v-navbar>
    </el-header>
    <el-main>
      <el-row>
        <el-col :offset="1" :span="6">
          <el-form ref="myInfo" :rules="info_rule" :model="myInfo" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="myInfo.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="职称">
              <el-input v-model="myInfo.type" disabled></el-input>
            </el-form-item>
            <el-form-item label="所属病区">
              <el-radio-group v-model="myInfo.section" size="small" disabled>
                <el-radio-button label="mild">轻症区</el-radio-button>
                <el-radio-button label="severe">重症区</el-radio-button>
                <el-radio-button label="critical">危重症区</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="我的姓名">
              <el-input v-model="myInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="出生年月">
              <el-date-picker v-model="myInfo.birth"
                              type="date" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submit('myInfo')">提交修改</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>


    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "MyInfo",
    data() {
      return {
        info_rule: {
          birth: [
            {required: true, message: '请选择正确的出生年月', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ],
        },
        myInfo: {
          username: this.$store.state.username,
          birth: '1970-1-1',
          name: 'name',
          type: this.$store.state.type,
          section: 'mild',
        }
      }
    }, created() {
      this.getUser();
    },
    methods: {
      getUser() {
        this.$axios.post('/user/info',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.myInfo.name = resp.data.name
          this.myInfo.section = resp.data.section
          this.myInfo.birth = resp.data.birth
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },
      submit(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.$axios.post('/user/modifyInfo', {
              username: this.$store.state.username,
              birth: this.myInfo.birth,
              name: this.myInfo.name,
            }).then(resp => {
              if (resp.data === 'success') {
                this.$notify({
                  title: '修改成功',
                  type: 'success'
                });
              } else {
                alert('修改失败')
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
