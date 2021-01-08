<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-container>
      <el-aside>
        <v-side :activeIndex="'1'"></v-side>
      </el-aside>
      <el-main>
        <h1>管理病人</h1>
        <el-row>
          <el-col>
            <el-form :inline="true" ref="selectRange" :model="selectRange">
              <el-cascader
                v-model="selectRange.value"
                :options="options"></el-cascader>
              <el-form-item>
                <el-button type="primary" @click="getPatients">查询</el-button>
              </el-form-item>
            </el-form>
          </el-col>

        </el-row>


        <el-table
          :data="tableData"
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
            prop="gender"
            label="性别">
            <template slot-scope="scope">
              <span v-if="scope.row.gender==='male'">男</span>
              <span v-else-if="scope.row.gender==='female'">女</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="age"
            label="年龄">
          </el-table-column>
          <el-table-column
            prop="level"
            label="病情等级">
            <template slot-scope="scope">
              <span v-if="scope.row.level==='mild'">轻症</span>
              <span v-else-if="scope.row.level==='severe'">重症</span>
              <span v-else-if="scope.row.level==='critical'">危重症</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="section"
            label="所属病区">
            <template slot-scope="scope">
              <span v-if="scope.row.status !==0">暂无</span>
              <span v-else-if="scope.row.section==='mild'">轻症区</span>
              <span v-else-if="scope.row.section==='severe'">重症区</span>
              <span v-else-if="scope.row.section==='critical'">危重症区</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="wardName"
            label="病房">
            <template slot-scope="scope">
              <span v-if="scope.row.status !==0" >暂无</span>
              <span v-else-if="scope.row.status ===0" >{{scope.row.wardName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sickbed"
            label="病床">
            <template slot-scope="scope">
              <span v-if="scope.row.status !==0">暂无</span>
              <span v-else-if="scope.row.sickbed===0">等待分配</span>
              <span v-else-if="scope.row.sickbed!==0">{{scope.row.sickbed}}号床</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="生存状态">
            <template slot-scope="scope">
              <span v-if="scope.row.status===1">康复出院</span>
              <span v-else-if="scope.row.status===0">在院治疗</span>
              <span v-else-if="scope.row.status===-1">死亡</span>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "EmergencyNurseCheckPatients",
    data() {
      return {
        selectRange:{
         value:[]
        },
        options: [{
          value: 'section',
          label: '病人所属病区',
          children: [{
            value: 'mild',
            label: '轻症区'
          }, {
            value: 'severe',
            label: '重症区'
          },{
            value:'critical',
            label:'危重症区'
          }]
        },{
          value: 'level',
          label: '病人病情评级',
          children: [{
            value: 'mild',
            label: '轻症'
          }, {
            value: 'severe',
            label: '重症'
          },{
            value:'critical',
            label:'危重'
          }]
        },{
          value: 'isWaiting',
          label: '是否在隔离区等待',
          children: [{
            value: 'true',
            label: '是'
          }, {
            value: 'false',
            label: '否'
          }]
        },{
          value: 'live',
          label: '病人生命状态',
          children: [{
            value: 'true',
            label: '正常'
          }, {
            value: 'false',
            label: '死亡'
          }]
        }

        ],
        tableData: [{
          id: 1,
          name: '',
          level: 'mild',
          section: 'mild',
          wardName:'',
          sickbed: 12,
          alive:true
        }]
      }
    },created(){
      this.getAllPatients();
    },methods:{
      getAllPatients(){
        this.$axios.post('/emergencyNurse/getPatientInfo',
          this.$store.state.username
        ).then(resp => {
          console.log(resp.data)
          this.tableData=resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },
      getPatients(){
        console.log(this.selectRange.value[0])
        console.log(this.selectRange.value[1])
        this.$axios.post('/emergencyNurse/select',{
          username:this.$store.state.username,
          type:this.selectRange.value[0],
          value:this.selectRange.value[1]
        }).then(resp=>{
          console.log(resp.data)
          this.tableData=resp.data
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
