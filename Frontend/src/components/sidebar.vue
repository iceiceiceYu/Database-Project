<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-vertical-demo" style="margin-top: 5px"
  @select="handleSelect">
    <div v-if="type === 'doctor'">
      <el-menu-item index="1" >
        <i class="el-icon-location"></i>
        <span>管理病人</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">我的病区</span>
      </el-menu-item>
      <el-menu-item index="3" >
        <i class="el-icon-document"></i>
        <span slot="title">新增核酸检测</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item>
    </div>
    <div v-else-if="type === 'chief nurse'">
      <el-menu-item index="1">
        <i class="el-icon-location"></i>
        <span>管理病人</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">管理护士</span>
      </el-menu-item>
      <el-menu-item index="3" >
        <i class="el-icon-document"></i>
        <span slot="title">管理病床</span>
      </el-menu-item>

    </div>
    <div v-else-if="type === 'ward nurse'">
      <el-menu-item index="1">
        <i class="el-icon-location"></i>
        <span>管理病人</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">病人信息登记</span>
      </el-menu-item>

    </div>
    <div v-else-if="type === 'emergency nurse'">
      <el-menu-item index="1">
        <i class="el-icon-location"></i>
        <span>查询病人信息</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">登记入院信息</span>
      </el-menu-item>
      <el-menu-item index="3" >
        <i class="el-icon-document"></i>
        <span slot="title">新增核酸检测</span>
      </el-menu-item>

    </div>

  </el-menu>
</template>

<script>
    export default {
        name: "v-side",
      props:{
          activeIndex:{
            type: String,
          default: '1'
        }
      },data(){
          return{
            type:this.$store.state.type
          }
      },methods:{
          handleSelect(key, keyPath) {
            if(this.type==='doctor'){
              switch (key) {
                case '1':
                  this.$router.replace({path: '/checkPatients'})
                  break;
                case '2':
                  this.$router.replace({path: '/checkStaff'})
                  break;
                case '3':
                  this.$router.replace({path: '/newReport'})
                  break;
              }
            }

          },

      }
    }
</script>

<style scoped>

</style>
