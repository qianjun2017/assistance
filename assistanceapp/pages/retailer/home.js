// pages/retailer/home.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    currentLottery: {},
    showCreate: false,
    stoping: false,
    showCurrent: false,
    showAcode: false,
    acode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
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
    app.ajaxGet({
      url: '/lottery/page',
      data: {
        pageSize: '1',
        retailerId: this.data.userInfo.id,
        status: 'normal',
        sort: 'l.id'
      },
      success: res => {
        if (res.success) {
          app.ajaxGet({
            url: '/lottery/get/' + res.data[0].id,
            success: l => {
              if (l.success) {
                this.setData({
                  currentLottery: l.data,
                  showCurrent: true
                })
              }
            }
          })
        } else {
          this.setData({
            showCreate: true
          })
        }
      }
    })
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

  bindRetailerTap: function(e) {
    wx.navigateTo({
      url: '../retailer/retailer'
    })
  },

  bindLotteryTap: function(e) {
    if(!this.data.userInfo.store){
      wx.navigateTo({
        url: '../retailer/retailer'
      })
    }
    wx.navigateTo({
      url: '../retailer/lottery'
    })
  },

  bindHistoryTap: function(e) {
    wx.navigateTo({
      url: '../retailer/history'
    })
  },

  bindStopTap: function(e) {
    wx.showModal({
      title: '提示',
      content: '确认停止活动？',
      success: res => {
        if (res.confirm) {
          this.setData({
            stoping: true
          })
          app.ajaxPost({
            url: '/lottery/over/' + this.data.currentLottery.id,
            success: res => {
              this.setData({
                stoping: false
              })
              if (res.success) {
                this.setData({
                  currentLottery: {},
                  showCreate: true,
                  showCurrent: false
                })
              }
            }
          })
        }
      }
    })
  },
  bindUpdateTap: function(e) {
    wx.navigateTo({
      url: '../retailer/lottery?lotteryId=' + this.data.currentLottery.id
    })
  },

  bindCloseTap: function () {
    this.setData({
      showAcode: false,
      interval: null
    })
  },
  /**
   * 二维码
   */
  bindAcodeTap: function (e) {
    this.setData({
      acode: app.globalData.service + '/wx/acode?page=pages/lottery/lottery&scene=' + this.data.currentLottery.id + '&v=' + Math.random(),
      showAcode: true
    })
  }
})