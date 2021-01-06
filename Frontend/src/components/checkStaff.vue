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
        <h1>管理病区</h1>
        <el-collapse>
          <el-collapse-item title="护士长信息" name="1">
            <el-table :data="chiefNurse"
                      stripe
                      style="width: 100%">
              <el-table-column
                prop="id"
                label="ID"
                width="50">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名">
              </el-table-column>
              <el-table-column
                prop="birth"
                label="出生年月">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="护士信息" name="2">
            <el-table :data="wardNurse"
                      stripe
                      style="width: 100%">
              <el-table-column
                prop="id"
                label="ID"
                width="50">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名">
              </el-table-column>
              <el-table-column
                prop="birth"
                label="出生年月">
              </el-table-column>
              <el-table-column
                prop="responsible"
                label="负责的病人">
              </el-table-column>
            </el-table>
          </el-collapse-item>

        </el-collapse>
      </el-main>
    </el-container>

  </el-container>
</template>

<script>
  export default {
    name: "checkStaff",
    data() {
      return {
        chiefNurse: [{
          id: 1,
          name: 'A',
          birth: '1999-01-01'
        }],
        wardNurse: [{
          id: 1,
          name: 'A',
          birth: '1999-01-01',
          responsible: []
        }]
      }
    }, created() {
      this.$axios.post('/doctor/wardNurse',
        this.$store.state.username
      ).then(resp => {
        this.wardNurse = resp.data
      })
        .catch(error => {
          console.log(error);
          alert('网络连接失败')
        })

      this.$axios.post('/doctor/chiefNurse',
        this.$store.state.username
      ).then(resp => {
        this.chiefNurse = resp.data
      })
        .catch(error => {
          console.log(error);
          alert('网络连接失败')
        })
    }
  }
</script>

<style scoped>

</style>
