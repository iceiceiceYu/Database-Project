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
        <p>病床管理</p>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
    export default {
        name: "ChiefNurseCheckWard"
    }
</script>

<style scoped>

</style>
