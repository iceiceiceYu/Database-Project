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
        <h1>每日信息</h1>
        <el-form :model="daily" ref="daily"
                 :rules="rules" label-width="80px">
          <el-form-item label="病人姓名">
            <el-input type="text" v-model="daily.patientName" disabled></el-input>
          </el-form-item>
          <el-form-item label="体温">
            <el-input type="text" v-model="daily.temperature"></el-input>
          </el-form-item>
          <el-form-item label="症状">
            <el-input type="text" v-model="daily.symptom"></el-input>
          </el-form-item>
          <el-form-item label="检测结果">
            <template>
              <el-radio v-model="daily.positive" :label="true">阳性</el-radio>
              <el-radio v-model="daily.positive" :label="false">阴性</el-radio>
            </template>
          </el-form-item>
          <el-form-item label="检测时间">
            <div class="block">
              <el-date-picker
                v-model="daily.date"
                align="right"
                type="date"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="submitClick('daily')">提交</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "DailyInfo",
    data() {
      return {
        patient:'',
        daily: {
          patientId: '',
          patientName: '',
          temperature: '38',
          symptom: '发烧',
          positive: true, // true - positive, false - negative
          date: '2020-01-01',
        }
      }
    },created() {
      this.patient=this.$route.query.patient
      this.daily.patientId=this.patient.id
      this.daily.patientName=this.patient.name
    },methods:{
      submitClick(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.$axios.post('/wardNurse/dailyInfo', {
              patientId: this.daily.patientId,
              patientName: this.daily.patientName,
              temperature: this.daily.temperature,
              symptom: this.daily.symptom,
              positive: this.daily.positive,
              date: this.daily.date,
              wardNurse: this.$store.state.username
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


    }
  }
</script>

<style scoped>

</style>
