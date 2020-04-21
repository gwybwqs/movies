import Vue from 'vue'
import axios from 'axios'
import Vuex from 'vuex'

Vue.use(Vuex)

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:8080'
export const loginCheck = (account, passwd) => {
  return axios.request({
    url: '/adminUser/check',
    method: 'get',
    params: {
      id: account,
      passwd: passwd
    }
  })
}

export const loginSave = (form) => {
  return axios.request({
    url: '/adminUser/save',
    method: 'post',
    data: form
  })
}

export const aList = (current, pageSize) => {
  return axios.request({
    url: '/adminUser/list',
    method: 'get',
    params: {
      current: current,
      pageSize: pageSize
    }
  })
}

export const aUpdate = (form) => {
  return axios.request({
    url: '/adminUser/update',
    method: 'post',
    data: form
  })
}

export const aDel = (form) => {
  return axios.request({
    url: '/adminUser/del',
    method: 'post',
    data: form
  })
}

export const aSearch = (searchThing, current, pageSize) => {
  return axios.request({
    url: '/adminUser/search',
    method: 'get',
    params: {
      id: searchThing,
      name: searchThing,
      current: current,
      pageSize: pageSize
    }
  })
}
