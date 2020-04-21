import Vue from 'vue'
import Router from 'vue-router'
import MainMovie from '@/view/MainMovie'
import Administrators from '@/view/Administrators'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MainMovie',
      component: MainMovie
    },
    {
      path: '/Administrators',
      name: 'Administrators',
      component: Administrators
    }
  ],
  mode: 'history'
})
