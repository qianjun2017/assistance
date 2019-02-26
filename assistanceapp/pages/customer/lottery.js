// pages/customer/lottery.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotteryId: '',
    lotteryCustomer: {},
    showAcode: false,
    acode: '',
    interval: undefined
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.lotteryId == undefined) {
      return
    } else {
      this.setData({
        lotteryId: options.lotteryId
      })
    }
    this.getLotteryCustomerData()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  getLotteryCustomerData: function () {
    app.ajaxGet({
      url: '/lottery/customer/page',
      data: { prize: true, customerId: app.globalData.userInfo.id, sort: 'lp.lotteryId', order: 'desc', page: '1', pageSize: '100', lotteryId: this.data.lotteryId },
      success: res => {
        if (res.success) {
          let lotteryCustomers = []
          let i = 0
          for (; i < res.data.length; i++) {
            let lotteryCustomer = res.data[i]
            let find = false
            let lci = 0
            for (; lci < lotteryCustomers.length; lci++) {
              let lc = lotteryCustomers[lci]
              if (lc.lotteryId == lotteryCustomer.lotteryId) {
                lc.list.push(lotteryCustomer)
                find = true
                break;
              }
            }
            if (!find) {
              let lc = {
                lotteryId: lotteryCustomer.lotteryId,
                no: lotteryCustomer.no,
                store: lotteryCustomer.store,
                address: lotteryCustomer.address,
                phone: lotteryCustomer.phone,
                list: []
              }
              lc.list.push(lotteryCustomer)
              lotteryCustomers.push(lc)
            }
          }
          this.setData({
            lotteryCustomer: lotteryCustomers[0]
          })
        }
      }
    })
  },
  /**
   * 兑奖
   */
  bindExchangeTap: function (e) {
    let data = e.currentTarget.dataset
    let prize = data.prize
    let self = this
    this.setData({
      acode: app.globalData.service + '/wx/acode?scene=' + prize.id + '&v=' + Math.random(),
      showAcode: true,
      interval: setInterval(function () {
        app.ajaxGet({
          url: '/lottery/customer/get/' + prize.id,
          success: res => {
            if (res.success) {
              if (res.data.status == 'exchanged') {
                let lotteryCustomers = self.data.lotteryCustomers
                let lotteryCustomer = {}
                for (lotteryCustomer in lotteryCustomers) {
                  if (lotteryCustomer.lotteryId == prize.lotteryId) {
                    let p = {}
                    for (p in lotteryCustomer.list) {
                      if (p.id == prize.id) {
                        p.status = res.data.status
                        break;
                      }
                    }
                    break;
                  }
                }
                self.setData({
                  showAcode: false,
                  interval: null,
                  lotteryCustomers: lotteryCustomers
                })
                wx.showToast({
                  title: '您成功兑换了' + prize.name,
                  icon: 'none',
                  duration: 1000
                })
              }
            }
          },
          fail: res => {
            self.setData({
              showAcode: false,
              interval: null
            })
            wx.showToast({
              title: '出问题了，请稍后再试',
              icon: 'none',
              duration: 1000
            })
          }
        })
      }, 2000)
    })
  },
  /**
   * 分享
   */
  onShareAppMessage: function (options) {
    if (options.from == 'button') {
      let data = options.target.dataset
      let prize = data.prize
      return {
        title: prize.store + '做活动啦！我领到了' + prize.name + "，你也来试试吧！",
        path: '/pages/lottery/lottery?lotteryId=' + prize.lotteryId + "&shareId=" + app.globalData.userInfo.id,
        imageUrl: ''
      }
    } else {
      return {
        title: '助销小工具助力商家快速营销'
      }
    }
  },

  bindCloseTap: function () {
    this.setData({
      showAcode: false,
      interval: null
    })
  }
})