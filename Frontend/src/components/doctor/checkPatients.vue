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
                :options="option"></el-cascader>
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
            label="病情等级"
          width="100">
            <template slot-scope="scope">
              <el-select v-model="scope.row.level" placeholder="请选择">
                <el-option label="轻症" value="mild"></el-option>
                <el-option label="重症" value="severe"></el-option>
                <el-option label="危重症" value="critical"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            prop="section"
            label="所属病区">
            <template slot-scope="scope">
              <span v-if="scope.row.section==='mild'">轻症区</span>
              <span v-else-if="scope.row.section==='severe'">重症区</span>
              <span v-else-if="scope.row.section==='critical'">危重症区</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sickBed"
            label="病床">
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
          <el-table-column  label="操作" width="500" fixed="right">
            <template slot-scope="scope">
              <div v-if="scope.row.status===0">
              <el-button size="mini" type="primary" @click="transLevel(scope.row)">提交病情评级修改</el-button>
              <el-button size="mini" @click="addReports(scope.row)">添加核酸检测单</el-button>
              <el-button size="mini" @click="transLive(scope.row)">更改生命状态</el-button>
              <el-button size="mini" v-if="section ==='mild'" @click="discharge(scope.row)">出院</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "DoctorCheckPatients",
    data() {
      return {
        section:this.$store.state.section,
        selectRange: {
          value: []
        },
        option: [{
          value: 'discharge',
          label: '是否满足出院条件',
          children: [{
            value: 'true',
            label: '满足'
          }, {
            value: 'false',
            label: '不满足'
          }]
        }, {
          value: 'trans',
          label: '是否待转入其他病区',
          children: [{
            value: 'true',
            label: '是'
          }, {
            value: 'false',
            label: '否'
          }]
        }, {
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
          sickBed: 12,
          status: 0
        }],
        selectionPatient: {},
        selection: '',
        level: 'mild'
      }
    }, created() {
      this.getAllPatients();
    }, methods: {
      getAllPatients() {
        this.section=this.$store.state.section
        this.$axios.post('/doctor/getPatientInfo',
          this.$store.state.username
        ).then(resp => {
          this.tableData = resp.data
          console.log(this.tableData)
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },
      getPatients() {
        this.$axios.post('/doctor/select', {
          username: this.$store.state.username,
          type: this.selectRange.value[0],
          value: this.selectRange.value[1]
        }).then(resp => {
          console.log(resp.data)
          this.tableData = resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      }, addReports(row) {
        this.selectionPatient = row
        this.$router.push({
          path: '/doctor/newReport',
          query: {
            patient: this.selectionPatient
          }
        })
      }, transLevel(row) {
        console.log(row)
        this.selectionPatient = row
        this.$axios.post('/doctor/modifyLevel', {
          patientId: this.selectionPatient.id,
          newLevel: row.level
        }).then(resp => {
            alert('添加成功')
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      }, transLive(row) {
        this.selectionPatient = row
        this.$axios.post('/doctor/modifyAlive',
         this.selectionPatient.id,
        ).then(resp => {
            alert('添加成功')
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },discharge(row){
        this.selectionPatient = row
        this.$axios.post('/doctor/discharge',
          this.selectionPatient.id,
        ).then(resp => {
            alert('添加成功')
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
