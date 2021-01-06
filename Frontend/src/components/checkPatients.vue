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
            prop="level"
            label="病情等级">
          </el-table-column>
          <el-table-column
            prop="section"
            label="所属病区">
          </el-table-column>
          <el-table-column
            prop="sickBed"
            label="病床">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "checkPatients",
    data() {
      return {
        selectRange:{
         value:[]
        },
        options: [{
          value: 'discharge',
          label: '是否满足出院条件',
          children: [{
              value: 'true',
              label: '满足'
            }, {
              value: 'false',
              label: '不满足'
            }]
        },{
          value: 'trans',
          label: '是否待转入其他病区',
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
          sickBed: 12
        }]
      }
    },created(){
      this.getAllPatients();
    },methods:{
      getAllPatients(){
        this.$axios.post('/doctor/getPatientInfo',
          this.$store.state.username
        ).then(resp => {
          this.tableData=resp.data
        })
          .catch(error => {
            console.log(error);
            alert('网络连接失败')
          })
      },
      getPatients(){
        this.$axios.post('/doctor/getPatients',{
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
