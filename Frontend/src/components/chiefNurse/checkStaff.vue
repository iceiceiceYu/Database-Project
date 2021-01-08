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
        <h1>管理护士</h1>
            <el-table :data="wardNurse"
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
              <el-table-column
                prop="birth"
                label="出生年月">
              </el-table-column>
              <el-table-column
                prop="responsible"
                label="负责的病人">
                <template slot-scope="scope">
                  {{patientsOfNurse[scope.$index]}}
                </template>
              </el-table-column>
              </el-table-column>
              <el-table-column
                label="操作">
                <template slot-scope="scope">
                  <el-button size="mini" type="danger" @click="deleteNurse(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
        <h1>新增护士</h1>
        <el-table :data="waitingNurse"
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
          <el-table-column
            prop="birth"
            label="出生年月">
          </el-table-column>
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="addNurse(scope.row)">添加</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

  </el-container>
</template>

<script>
  export default {
    name: "ChiefNurseCheckStaff",
    data() {
      return {
        waitingNurse:[],
        patientsOfNurse:[],
        newNurse: {
          name: '',
          birth: '1980-01-01'
        },
        wardNurse: [{
          id: 1,
          name: 'A',
          birth: '1999-01-01',
          responsible: []
        }]
      }
    }, created() {
      this.$axios.post('/chiefNurse/wardNurse',
        this.$store.state.username
      ).then(resp => {
        this.wardNurse = resp.data
      })
        .catch(error => {
          console.log(error);
          alert('网络连接失败')
        })

      this.$axios.post('/chiefNurse/searchBackupWard',
        this.$store.state.username
      ).then(resp => {
        this.waitingNurse = resp.data
      })
        .catch(error => {
          console.log(error);
          alert('网络连接失败')
        })

      this.$axios.post('/chiefNurse/patientsOfNurse',
        this.$store.state.username
      ).then(resp => {
        this.patientsOfNurse=resp.data
        console.log(this.patientsOfNurse)
      })
        .catch(error => {
          console.log(error);
          alert('网络连接失败')
        })
    },
    methods: {
      deleteNurse(row){
        this.$axios.post('/chiefNurse/deleteNurse',{
            chiefNurse:this.$store.state.username,
            nurseName:row.name
        }).then(resp => {
          if(resp.data === 'success'){
            alert('删除成功！')
          }else {
            alert('目前情况不支持删除')
          }
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },
      addNurse(row){
        this.$axios.post('/chiefNurse/newNurse',{
          chiefNurse:this.$store.state.username,
          nurseName:row.name
        }).then(resp => {
            alert('添加成功！')
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      }
    }
  }
</script>

<style scoped>

</style>
