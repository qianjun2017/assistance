import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        isLogin: false,
        user: null,
        permissions: [],
        key: ''
    },
    mutations: {
        login(state) {
            state.isLogin = true
            sessionStorage.setItem('isLogin', 'true')
        },
        logout(state) {
            state.isLogin = false,
                state.user = null,
                state.permissions = []
            sessionStorage.clear()
        },
        setUser(state, userData) {
            state.user = userData
            sessionStorage.setItem('user', JSON.stringify(userData));
        },
        setPermissions(state, permissions) {
            state.permissions = permissions
            sessionStorage.setItem('permissions', JSON.stringify(permissions));
        },
        setKey(state, key) {
            state.key = key
            sessionStorage.setItem('key', key)
        }
    },
    getters: {
        logined: function(state) {
            if (!state.isLogin) {
                if (sessionStorage.getItem('isLogin') == 'true') {
                    state.isLogin = true
                }
            }
            return state.isLogin
        },
        getUser: function(state) {
            if (state.user == null) {
                state.user = JSON.parse(sessionStorage.getItem('user'))
            }
            return state.user
        },
        getPermissions: function(state) {
            if (state.permissions == []) {
                state.permissions = JSON.parse(sessionStorage.getItem('permissions'))
            }
            return state.permissions
        },
        getKey: function(state) {
            if (state.key == '') {
                state.key = sessionStorage.getItem('key')
            }
            return state.key
        }
    }
})

export default store