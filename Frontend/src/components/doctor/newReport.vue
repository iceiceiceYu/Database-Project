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
        <h1>核酸检测单</h1>
        <el-form :model="report" ref="report"
                 :rules="rules" label-width="80px">
          <el-form-item label="病人姓名">
            <el-input type="text" v-model="report.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="性别" >
            <template>
              <el-radio v-model="report.gender" label="male" disabled>男</el-radio>
              <el-radio v-model="report.gender" label="female" disabled>女</el-radio>
            </template>
          </el-form-item>
          <el-form-item label="年龄" >
            <el-input type="text" v-model="report.age" disabled></el-input>
          </el-form-item>

          <el-form-item label="检测结果">
            <template>
              <el-radio v-model="report.positive" :label="true">阳性</el-radio>
              <el-radio v-model="report.positive" :label="false">阴性</el-radio>
            </template>
          </el-form-item>
          <el-form-item label="病情等级">
            <el-radio-group v-model="report.level" size="small">
              <el-radio-button label="mild">轻症</el-radio-button>
              <el-radio-button label="severe">重症</el-radio-button>
              <el-radio-button label="critical">危重症</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="检测时间">
            <div class="block">
              <el-date-picker
                v-model="report.date"
                align="right"
                type="datetime"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="submitClick('report')">提交</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "DoctorNewReport",
    data() {
      return {
        patient:{},
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }]
        },
        rules:{
          date:[{required:true, message: '请选择正确的日期', trigger: 'blur'}],
          level:[{required:true, message: '请选择正确的等级', trigger: 'blur'}]
        },
        report: {
          name: '1',
          gender:'male',
          age:32,
          positive:true,
          level: '',
          date:'',
        }
      }
    },created(){
      this.getParams()
    },
    methods:{
      getParams(){
        this.patient = this.$route.query.patient
        this.report.name=this.patient.name
        this.report.gender=this.patient.gender
        this.report.age=this.patient.age
      },
      submitClick(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.$axios.post('/doctor/addReport', {
              doctor:this.$store.state.username,
              patientId:this.patient.id,
              patientName: this.report.name,
              positive:this.report.positive,
              level: this.report.level,
              date:this.report.date,
            }).then(resp => {
              this.$notify({
                title: '添加成功',
                type: 'success'
              });
            })
              .catch(error => {
                console.log(error);
                alert('网络连接失败')
              })
          }
        })
      }
    },watch:{
      $route: 'getParams'
    }
  }
</script>

<style scoped>

</style>
