// pages/customer/index.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotteryCustomers: [],
    lotteryCustomerPage: 1,
    lotteryCustomerPages: 0,
    scrollHight: 0,
    loading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.getSystemInfo({
      success: res => {
        this.setData({
          scrollHight: res.windowHeight
        })
        this.getLotteryCustomerData()
      }
    })
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
    if (this.data.loading) {
      return
    }
    wx.showNavigationBarLoading()
    this.setData({
      lotteryCustomerPage: 1
    })
    this.getLotteryCustomerData()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.loading) {
      return
    }
    if (this.data.lotteryCustomerPages > this.data.lotteryCustomerPage) {
      wx.showLoading({
        title: '加载中...'
      })
      this.setData({
        lotteryCustomerPage: this.data.lotteryCustomerPage + 1
      })
      this.getLotteryCustomerData()
    }else{
      wx.showToast({
        title: '没有更多数据了',
        icon: 'none'
      })
    }
  },
  getLotteryCustomerData: function () {
    this.setData({
      loading: true
    })
    app.ajaxGet({
      url: '/lottery/customer/page',
      data: { prize: true, customerId: app.globalData.userInfo.id, sort: 'lp.lotteryId', order: 'desc', page: this.data.lotteryCustomerPage },
      success: res => {
        if (res.success) {
          let lotteryCustomers = []
          if (res.page > 1) {
            lotteryCustomers.concat(this.data.lotteryCustomers)
          }
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
            lotteryCustomers: lotteryCustomers,
            lotteryCustomerPages: res.pages
          })
          wx.hideNavigationBarLoading()
          wx.stopPullDownRefresh()
          wx.hideLoading()
        }
        this.setData({
          loading: false
        })
      }
    })
  },

  bindCustomerLotteryTap: function (e) {
    let data = e.currentTarget.dataset
    wx.navigateTo({
      url: '../customer/lottery?lotteryId=' + data.id
    })
  }
})