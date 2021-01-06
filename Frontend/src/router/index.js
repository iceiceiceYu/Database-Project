import Vue from 'vue'
import Router from 'vue-router'
import ChangePass from '@/components/changePass'
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'
import MyInfo from '@/components/MyInfo'
import CheckPatients from '@/components/checkPatients'
import CheckStaff from '@/components/checkStaff'
import newReport from '@/components/newReport'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path:'/myInfo',
      name:'MyInfo',
      component:MyInfo
    },
    {
      path:'/changePass',
      name:'ChangePass',
      component:ChangePass
    },
    {
      path:'/checkPatients',
      name:'CheckPatients',
      component:CheckPatients
    },
    {
      path:'/checkStaff',
      name:'CheckStaff',
      component:CheckStaff
    },
    {
      path:'/newReport',
      name:'newReport',
      component:newReport
    }
  ]
})
