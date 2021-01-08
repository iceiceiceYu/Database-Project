<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-container>
      <el-aside>
        <v-side :activeIndex="'4'"></v-side>
      </el-aside>
      <el-main>
        <h1>新消息</h1>
        <el-alert
          v-for="message in messageList"
          :title="message"
          v-bind:key="message"
          type="info"
          show-icon
          :closable="false"
          style="margin-top: 10px">
        </el-alert>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "ChiefNews",
    data(){
      return{
        messageList:['1','2','3']
      }
    },created(){
      this.$axios.post('/chiefNurse/getMessage',
        this.$store.state.username
      ).then(resp => {
        this.messageList=resp.data
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
