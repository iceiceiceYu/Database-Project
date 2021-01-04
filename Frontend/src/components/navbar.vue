<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
           @select="handleSelect">
    <el-menu-item index="1">工作台</el-menu-item>
    <el-menu-item index="2">个人信息修改</el-menu-item>

    <el-menu-item style="float: right" @click="dialogVisible=true">
      登出
    </el-menu-item>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>是否确认退出？</span>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="logout">确 定</el-button>
          </span>
    </el-dialog>
    <el-menu-item style="float: right">
      <i class="el-icon-user-solid"></i>用户名
    </el-menu-item>
  </el-menu>
</template>

<script>
    export default {
        name: "v-navbar",
      props:{
        activeIndex:{
          type: String,
          default:'1'
        }
      },
      data() {
        return {
          dialogVisible: false,
        };
      },
      methods: {
        handleSelect(key, keyPath) {
          switch (key) {
            case '1':
              this.$router.replace({path: '/dashboard'})
              break;
            case '2':
              this.$router.replace({path: '/myInfo'})
              break;
          }
        },
        logout() {
          this.$router.replace({path: '/login'})
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {
            });
        }
      }
    }
</script>

<style scoped>

</style>
