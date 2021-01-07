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
      <p>登记入院信息</p>
    </el-main>
    </el-container>
  </el-container>
</template>

<script>
    export default {
        name: "CheckIn"
    }
</script>

<style scoped>

</style>
