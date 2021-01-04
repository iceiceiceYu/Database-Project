<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'2'"></v-navbar>
    </el-header>
    <el-main>
      <el-row>
        <el-col :span="12">
          <el-form ref="myInfo" :model="myInfo" label-width="80px">
            <el-form-item label="我的姓名">
              <el-input v-model="myInfo.name" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="myInfo.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="职称">
              <el-input v-model="myInfo.title" disabled></el-input>
            </el-form-item>
            <el-form-item label="病区">
              <el-radio-group v-model="myInfo.section" size="small">
                <el-radio-button label="number1">病区1</el-radio-button>
                <el-radio-button label="number2">病区2</el-radio-button>
                <el-radio-button label="number3">病区3</el-radio-button>
              </el-radio-group>
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
      data(){
          return{
            myInfo:{
              name:'name',
              username:'username',
              section:'number1',
              title:'doctor',
              password:'password',


            }
          }
      },
      methods:{

      }
    }
</script>

<style scoped>

</style>
