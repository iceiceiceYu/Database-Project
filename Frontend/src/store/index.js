import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || null,
    userDetails: localStorage.getItem('userDetails') || null
  },
  mutations: {
    login(state, data){
      localStorage.setItem('token', data.token)
      localStorage.setItem('userDetails', data.userDetails)
      state.user = data.userDetails
      state.token = data.token
    },
    logout(state) {
      // 移除token
      localStorage.removeItem('token')
      localStorage.removeItem('userDetails')
      state.userDetails = null
      state.token = null
    }
  },
  actions: {
  }
})
