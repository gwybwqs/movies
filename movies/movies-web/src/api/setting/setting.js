import Vue from 'vue'
import axios from 'axios'

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:8080'

export const tList = () => {
  return axios.request({
    url: '/touristSetting/list',
    method: 'get'
  })
}

export const uList = () => {
  return axios.request({
    url: '/userSetting/list',
    method: 'get'
  })
}
export const tUpdate = (form) => {
  return axios.request({
    url: '/touristSetting/update',
    method: 'post',
    data: form
  })
}
export const userUpdate = (form) => {
  return axios.request({
    url: '/userSetting/update',
    method: 'post',
    data: form
  })
}
