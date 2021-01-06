<template>
  <el-container>
    <el-header>
      <v-navbar :activeIndex="'1'"></v-navbar>
    </el-header>
    <el-aside>
       <v-side :activeIndex="'1'"></v-side>
    </el-aside>
    <el-main>

    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Dashboard",
    data() {
      return {

      };
    },
    methods: {
    }
  }
</script>

<style scoped>

</style>
