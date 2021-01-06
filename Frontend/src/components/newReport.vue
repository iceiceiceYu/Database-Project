<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-aside>
      <v-side :activeIndex="'1'"></v-side>
    </el-aside>
    <el-main>
      <p>管理病人</p>
    </el-main>
  </el-container>
</template>

<script>
    export default {
        name: "checkPatients"
    }
</script>

<style scoped>

</style>
