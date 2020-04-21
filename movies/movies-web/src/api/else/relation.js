import Vue from 'vue'
import axios from 'axios'
import Vuex from 'vuex'

Vue.use(Vuex)

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:8080'

export const relationSave = (form) => {
  return axios.request({
    url: '/relation/add',
    method: 'post',
    data: form
  })
}
