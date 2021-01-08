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
      <p>新消息</p>
    </el-main>
    </el-container>
  </el-container>
</template>

<script>
    export default {
        name: "DoctorNews"
    }
</script>

<style scoped>

</style>
