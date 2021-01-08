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
          :data="wardInfo"
          stripe
          style="width: 100%"
          height="500">
          <el-table-column
            label="病房名">
            <template slot-scope="scope">
              {{wardInfo[scope.$index]}}
            </template>
          </el-table-column>
          <el-table-column
            prop="sickBedInfo"
            label="病床">
            <template slot-scope="scope">
              {{sickbedInfo[scope.$index]}}
            </template>
          </el-table-column>
          <el-table-column
            prop="patientInfo"
            label="患者">
            <template slot-scope="scope">
              {{patientInfo[scope.$index]}}
            </template>
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
            wardInfo:[],
            sickbedInfo:[],
            patientInfo:[]
          }
      },created(){
        this.$axios.post('/chiefNurse/wardInfo',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.wardInfo=resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
        this.$axios.post('/chiefNurse/sickbedInfo',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.sickbedInfo=resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
        this.$axios.post('/chiefNurse/patientInfo',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.patientInfo=resp.data
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
