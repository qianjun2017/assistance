import axios from 'axios';
import Message from 'element-ui/packages/message/index.js';
import router from '@/router'

axios.defaults.baseURL = process.env.BASE_URL
axios.defaults.timeout = 60000;
axios.defaults.maxRedirects = 2;
axios.defaults.withCredentials = true;

function toType(obj) {
    return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
}

function filterNull(o) {
    for (var key in o) {
        if (o[key] === null) {
            delete o[key]
        }
        if (toType(o[key]) === 'string') {
            o[key] = o[key].trim()
        } else if (toType(o[key]) === 'object') {
            o[key] = filterNull(o[key])
        } else if (toType(o[key]) === 'array') {
            o[key] = filterNull(o[key])
        }
    }
    return o
}

function apiAxios(method, url, params) {
    if (params) {
        params = filterNull(params)
    }
    return new Promise(function(resolve, reject) {
        axios({
            method: method,
            url: url,
            data: (method === 'POST' || method === 'PUT') ? params : null,
            params: (method === 'GET' || method === 'DELETE') ? params : null
        }).then(function(response) {
            if (response.status === 200) {
                if (!response.data.success) {
                    if (response.data.message === '没有权限') {
                        router.push({ path: '/unauth' })
                    } else if (response.data.message === '没有登陆或session已过期') {
                        router.push({ path: '/expired' })
                    }
                }
                resolve(response.data)
            } else {
                reject(response)
            }
        }, function(error) {
            Message({
                message: '网络错误，请稍后再试',
                type: 'error'
            })
        })
    })
}

export default {
    get: function(url, params) {
        return apiAxios('GET', url, params)
    },
    post: function(url, params, success, failure) {
        return apiAxios('POST', url, params).then(function(res) {
            if (!res.success) {
                Message({
                    message: res.message,
                    type: 'error'
                })
            }
            return Promise.resolve(res)
        })
    },
    serviceUrl: axios.defaults.baseURL,
    imageServiceUrl: axios.defaults.baseURL
}