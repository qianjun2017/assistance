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
    wx.showNavigationBarLoading()
    this.setData({
      lotteryPage: 1
    })
    this.getLotteryData()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log('加载更多')
    if (this.data.loading) {
      return
    }
    if (this.data.lotteryPages > this.data.lotteryPage) {
      wx.showLoading({
        title: '加载中...'
      })
      this.setData({
        lotteryPage: this.data.lotteryPage + 1
      })
      this.getLotteryData()
    } else {
      wx.showToast({
        title: '没有更多数据了',
        icon: 'none'
      })
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
    app.ajaxGet({
      url: '/lottery/page',
      data: { retailerId: app.globalData.userInfo.id, sort: 'l.createTime', order: 'desc', page: this.data.lotteryPage, status: 'over' },
      success: res => {
        if (res.success) {
          let lotterys = []
          if(res.page>1){
            lotterys.concat(this.data.lotterys)
          }
          this.setData({
            lotterys: lotterys.concat(res.data),
            lotteryPages: res.pages
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
  }
})