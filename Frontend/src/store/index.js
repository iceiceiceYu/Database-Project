import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    id: localStorage.getItem('id') || null,
    username: localStorage.getItem('username') || null,
    type:localStorage.getItem('type')||null
  },
  mutations: {
    login(state, data){
      localStorage.setItem('id', data.id)
      localStorage.setItem('username', data.username)
      localStorage.setItem('type',data.type)
      state.id = data.id
      state.username = data.username
      state.type =data.type
    },
    logout(state) {
      // 移除token
      localStorage.removeItem('id')
      localStorage.removeItem('username')
      localStorage.removeItem('type')
      state.id = null
      state.username = null
      state.type = null
    }
  },
  actions: {
  }
})
