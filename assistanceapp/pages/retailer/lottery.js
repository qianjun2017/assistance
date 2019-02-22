// pages/retailer/lottery.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotteryId: undefined,
    createLottery: true,
    lottery: {},
    loading: false,
    notice: '',
    start: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      lotteryId: options.lotteryId
    })
    let now = new Date()
    let year = now.getFullYear()
    let month = now.getMonth()+1
    let day = now.getDate()
    this.setData({
      start: year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '')+day
    })
    if(this.data.lotteryId){
      this.setData({
        createLottery: false
      })
    }
    app.ajaxGet({
      url: '/lottery/info',
      data: { lotteryId: this.data.lotteryId},
      suceess: res => {
        if(res.success){
          this.setData({
            lottery: res.data
          })
        }
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

  bindLastExchangeTimeChange: function (e) {
    let lottery = this.data.lottery
    lottery.lastExchangeTime = e.detail.value
    this.setData({
      lottery: lottery
    })
  },

  /**
   * 输入参与次数
   */
  bindCountInput: function (e) {
    let lottery = this.data.lottery
    lottery.count = e.detail.value
    this.setData({
      lottery: lottery
    })
  },

  /**
   * 校验参与次数
   */
  bindCountValid: function (e) {
    let rx = new RegExp('^[1-9][0-9]{0，3}$')
    console.log(e.detail.value)
    console.log(rx.test(e.detail.value))
    if (rx.test(e.detail.value)) {
      console.log('有效手机号')
    } else {
      console.log('手机号无效')
    }
  },

  bindSubmitTap: function () {
    app.ajaxPost({
      url: '/lottery/'+(this.data.createLottery?'add':'update'),
      data: this.data.lottery,
      success: res => {
        this.setData({
          loading: false
        })
        if(res.success){
          wx.navigateTo({
            url: '../retailer/home'
          })
        } else {
          this.setData({
            notice: res.message
          })
        }
      },
      fail: res => {
        this.setData({
          loading: true
        })
      }
    })
  }
})