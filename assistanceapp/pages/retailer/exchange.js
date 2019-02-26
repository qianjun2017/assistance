// pages/retailer/exchange.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotteryCustomerId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var scene = decodeURIComponent(options.scene)   // 扫码进入

    this.setData({
      lotteryCustomerId: scene == 'undefined' ? '' : scene
    })

    if(this.data.lotteryCustomerId==''){
      wx.showModal({
        title: '错误提示',
        content: '恭喜您！您抽中了' + self.data.prize.name + '！',
        showCancel: true
      })
    }
    app.ajaxPost({
      url: '/lottery/customer/exchange',
      data: {
        lotteryCustomerId: this.data.lotteryCustomerId
      },
      success: res => {
        let title = ""
        let content = ""
        if(res.success){
          title = '成功提示'
          content: '恭喜您！您成功兑换了' + res.data + '！'
        }else{
          title = '错误提示'
          content: res.message
        }
        wx.showModal({
          title: title,
          content: content,
          showCancel: true,
          complete: r => {
            wx.navigateBack({
              delta: 1
            })
          }
        })
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

  }
})