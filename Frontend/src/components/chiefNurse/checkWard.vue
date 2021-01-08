<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-container>
      <el-aside>
        <v-side :activeIndex="'3'"></v-side>
      </el-aside>
      <el-main>
        <h1>病床管理</h1>
        <el-table
          :data="bedData"
          stripe
          style="width: 100%">
          <el-table-column
            prop="id"
            label="ID"
            width="50">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
    export default {
        name: "ChiefNurseCheckWard",
      data(){
          return{
            bedData:[{
            }]
          }
      },created(){

      },methods:{

      }
    }
</script>

<style scoped>

</style>
