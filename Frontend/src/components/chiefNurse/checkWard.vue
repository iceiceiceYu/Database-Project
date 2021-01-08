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
          :data="wardData"
          stripe
          style="width: 100%">
          <el-table-column
            prop="wardName"
            label="病房名">
          </el-table-column>
          <el-table-column
            prop="sickBed"
            label="病床">
          </el-table-column>
          <el-table-column
            prop="patient"
            label="患者">
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
            wardData:[{
              wardName:'病房1',
              sickBed:'1',
              patient:'zhang'
            }]
          }
      },created(){
        this.$axios.post('/chiefNurse/getWardInfo',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.wardData=resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },methods:{

      }
    }
</script>

<style scoped>

</style>
