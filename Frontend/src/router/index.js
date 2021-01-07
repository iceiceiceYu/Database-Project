import Vue from 'vue'
import Router from 'vue-router'
import ChangePass from '@/components/changePass'
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'
import MyInfo from '@/components/MyInfo'
import DoctorCheckPatients from '@/components/doctor/checkPatients'
import DoctorCheckStaff from '@/components/doctor/checkStaff'
import DoctorNewReport from '@/components/doctor/newReport'
import ChiefNurseCheckPatients from '@/components/chiefNurse/checkPatients'
import ChiefNurseCheckStaff from '@/components/chiefNurse/checkStaff'
import ChiefNurseCheckWard from '@/components/chiefNurse/checkWard'
import WardNurseCheckPatients from '@/components/wardNurse/checkPatients'
import DailyInfo from '@/components/wardNurse/dailyInfo'
import EmergencyNurseCheckPatients from '@/components/emergencyNurse/checkPatients'
import CheckIn from '@/components/emergencyNurse/checkIn'

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
      path:'/doctor/checkPatients',
      name:'DoctorCheckPatients',
      component:DoctorCheckPatients
    },
    {
      path:'/doctor/checkStaff',
      name:'DoctorCheckStaff',
      component:DoctorCheckStaff
    },
    {
      path:'/doctor/newReport',
      name:'DoctorNewReport',
      component:DoctorNewReport
    },
    {
      path:'/chiefNurse/checkPatients',
      name:'ChiefNurseCheckPatients',
      component:ChiefNurseCheckPatients
    },
    {
      path:'/chiefNurse/checkStaff',
      name:'ChiefNurseCheckStaff',
      component:ChiefNurseCheckStaff
    },
    {
      path:'/chiefNurse/checkWard',
      name:'ChiefNurseCheckWard',
      component:ChiefNurseCheckWard
    },{
      path:'/wardNurse/checkPatients',
      name:'WardNurseCheckPatients',
      component:WardNurseCheckPatients
    },{
      path:'/wardNurse/dailyInfo',
      name:'DailyInfo',
      component:DailyInfo
    },{
      path:'/emergencyNurse/checkPatients',
      name:'EmergencyNurseCheckPatients',
      component:EmergencyNurseCheckPatients
    },{
      path:'/emergencyNurse/checkIn',
      name:'CheckIn',
      component:CheckIn
    }
  ]
})
