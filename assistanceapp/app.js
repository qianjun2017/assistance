//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        this.ajaxGet({
          url: '/wx/openid',
          data: { code: res.code },
          success: r => {
            if (r.success) {
              this.ajaxGet({
                url: '/customer/info',
                data: { openid: r.data},
                success: k => {
                  if(k.success){
                    this.userInfo = k.data
                  }else{
                    if(k.data == 404){
                      this.ajaxPost({
                        url: '/customer/register',
                        data: { openid: r.data},
                        success: q => {
                          if (q.success) {
                            this.ajaxGet({
                              url: '/customer/info',
                              data: { openid: r.data },
                              success: p => {
                                if (p.success) {
                                  this.userInfo = p.data
                                }
                              }
                            })
                          }
                        }
                      })
                    }
                  }
                }
              })
            }
          }
        })
      }
    })
  },
  globalData: {
    userInfo: null,
    service: 'http://localhost:8080'
  },
  ajaxGet: function (config) {
    wx.request({
      url: this.globalData.service + config.url,
      data: config.data || {},
      header: { 'Content-Type': 'application/x-www-form-urlencoded' },
      success: function (res) {
        if (config.success) {
          config.success(res.data)
        }
      }
    })
  },
  ajaxPost: function (config) {
    wx.request({
      url: this.globalData.service + config.url,
      data: config.data || {},
      method: 'POST',
      success: function (res) {
        if (config.success) {
          config.success(res.data)
        }
      }
    })
  }
})