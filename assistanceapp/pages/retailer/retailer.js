// pages/retailer/retailer.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: '',
    store: '',
    phone: '',
    notice: '',
    loading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      phone: app.globalData.userInfo.phone,
      store: app.globalData.userInfo.store,
      address: app.globalData.userInfo.address
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

  /**
   * 输入商家手机号码
   */
  bindPhoneInput: function(e) {
    this.setData({
      phone: e.detail.value
    })
  },

  /**
   * 校验商家手机号码
   */
  bindPhoneValid: function (e) {
    let rx = new RegExp('^1[34578]\d{9}$')
    console.log(e.detail.value)
    console.log(rx.test(e.detail.value))
    if (rx.test(e.detail.value)) {
      console.log('有效手机号')
    } else {
      console.log('手机号无效')
    }
  },

  /**
   * 输入店铺名称
   */
  bindStoreInput: function (e) {
    this.setData({
      store: e.detail.value
    })
  },

  /**
   * 输入店铺地址
   */
  bindAddressInput: function (e) {
    this.setData({
      address: e.detail.value
    })
  },

  bindSubmitTap: function(e) {
    this.setData({
      loading: true
    })
    app.ajaxPost({
      url: '/customer/retailer',
      data: {
        customerId: app.globalData.userInfo.id,
        store: this.data.store,
        phone: this.data.phone,
        address: this.data.address
      },
      success: res => {
        this.setData({
          loading: false
        })
        if(res.success){
          app.globalData.userInfo.store = this.data.store
          app.globalData.userInfo.phone = this.data.phone
          app.globalData.userInfo.address = this.data.address
          app.globalData.userInfo.retailer = true
          wx.navigateTo({
            url: '../retailer/home'
          })
        }else{
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