import Vue from 'vue'
import axios from 'axios'

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:8080'

export const mList = (current, pageSize) => {
  return axios.request({
    url: '/movies/list',
    method: 'get',
    params: {
      current: current,
      pageSize: pageSize
    }
  })
}

export const mUpdate = (form) => {
  return axios.request({
    url: '/movies/update',
    method: 'post',
    data: form
  })
}

export const mAdd = (form) => {
  return axios.request({
    url: '/movies/add',
    method: 'post',
    data: form
  })
}

export const mDel = (form) => {
  return axios.request({
    url: '/movies/del',
    method: 'post',
    data: form
  })
}

export const mSearch = (searchThing, current, pageSize) => {
  return axios.request({
    url: '/movies/search',
    method: 'get',
    params: {
      name: searchThing,
      current: current,
      pageSize: pageSize
    }
  })
}

export const show = (current, pageSize) => {
  return axios.request({
    url: '/movies/show',
    method: 'get',
    params: {
      current: current,
      pageSize: pageSize
    }
  })
}

export const typeShow = (type, current, pageSize) => {
  return axios.request({
    url: '/movies/showType',
    method: 'get',
    params: {
      type: type,
      current: current,
      pageSize: pageSize
    }
  })
}

export const highScoreShow = (current, pageSize) => {
  return axios.request({
    url: '/movies/highScore',
    method: 'get',
    params: {
      current: current,
      pageSize: pageSize
    }
  })
}

export const likeShow = (id, current, pageSize) => {
  return axios.request({
    url: '/movies/like',
    method: 'get',
    params: {
      id: id,
      current: current,
      pageSize: pageSize
    }
  })
}

export const searchNameOrPerformer = (searchValue, current, pageSize) => {
  return axios.request({
    url: '/movies/searchList',
    method: 'get',
    params: {
      name: searchValue,
      performan: searchValue,
      current: current,
      pageSize: pageSize
    }
  })
}
