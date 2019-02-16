// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import 'font-awesome/css/font-awesome.min.css'
import VueLazyload from 'vue-lazyload'

Vue.use(ElementUI)

Vue.use(VueLazyload, { attempt: 10 })

import api from './api/index'

Vue.config.productionTip = false

Vue.prototype.$ajax = api

Vue.prototype.$service = api.serviceUrl

import util from './util/index'

Vue.prototype.$util = util

Vue.directive('hasPermission', {
    bind: function(el, binding, vnode) {
        let permissions = JSON.parse(sessionStorage.getItem('permissions'))
        var noPermission = true
        if(binding.value==undefined || binding.value == ''){
            noPermission = false
        }else if (permissions && permissions.length) {
            var permission = binding.value.split('|')
            var p
            for (p in permission) {
                if (permissions.includes(permission[p])) {
                    noPermission = false
                    break
                }
            }
        }
        if (noPermission) {
            el.parentNode.removeChild(el)
        }
    }
})

Vue.prototype.$contains = function(arr, val) {
    if (arr == null) {
        return false
    }
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === val) {
            return true
        }
    }
    return false
}

router.beforeEach((to, from, next) => {
    if (to.meta.hasOwnProperty('isLogin') && to.meta.isLogin || to.meta.hasOwnProperty('permission')) {
        var logined = sessionStorage.isLogin
        if (logined == 'true') {
            next()
        } else {
            next('login')
        }
    } else {
        next()
    }
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: { App },
    template: '<App/>'
})