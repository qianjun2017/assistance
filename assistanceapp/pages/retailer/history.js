// pages/retailer/history.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotterys: [],
    lotteryPage: 1,
    lotteryPages: 0,
    loading: false,
    scrollHight: 0
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
        this.getLotteryData()
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
    this.setData({
      lotteryPage: 1,
      lotterys: []
    })
    this.getLotteryData()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.loading) {
      return
    }
    if (this.data.lotteryPages > this.data.lotteryPage) {
      this.setData({
        lotteryPage: this.data.lotteryPage + 1
      })
      this.getLotteryData()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },

  getLotteryData: function () {
    this.setData({
      loading: true
    })
    wx.showLoading({
      title: '加载中'
    })
    app.ajaxGet({
      url: '/lottery/page',
      data: { retailerId: app.globalData.userInfo.id, sort: 'l.createTime', order: 'desc', page: this.data.lotteryPage, status: 'over' },
      success: res => {
        if (res.success) {
          let lotterys = this.data.lotterys
          this.setData({
            lotterys: lotterys.concat(res.data),
            lotteryPages: res.pages,
            loading: false
          })
          wx.hideLoading()
        }
      }
    })
  }
})