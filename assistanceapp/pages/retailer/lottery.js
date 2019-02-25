// pages/retailer/lottery.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    lotteryId: '',
    createLottery: true,
    lottery: {},
    loading: false,
    notice: '',
    start: '',
    share: 0,
    shareArray: ['请选择是否必须分享才能兑换', '是', '否'],
    same: 0,
    sameArray: ['请选择是否可以重复获取相同奖品', '是', '否']
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let now = new Date()
    let year = now.getFullYear()
    let month = now.getMonth()+1
    let day = now.getDate()
    this.setData({
      start: year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '')+day
    })
    if(this.data.lotteryId==''){
      return
    }else{
      this.setData({
        lotteryId: options.lotteryId
      })
    }
    this.setData({
      createLottery: false
    })
    app.ajaxGet({
      url: '/lottery/info',
      data: { lotteryId: this.data.lotteryId},
      success: res => {
        if(res.success){
          this.setData({
            lottery: res.data,
            share: res.data.share ? 1 : 2,
            same: res.data.same ? 1 : 2
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

  bindShareChange: function (e) {
    let lottery = this.data.lottery
    if (e.detail.value==1){
      lottery.share = true
    }else{
      lottery.share = false
    }
    this.setData({
      share: e.detail.value,
      lottery: lottery
    })
  },

  bindSameChange: function (e) {
    let lottery = this.data.lottery
    if (e.detail.value == 1) {
      lottery.same = true
    } else {
      lottery.same = false
    }
    this.setData({
      same: e.detail.value,
      lottery: lottery
    })
  },

  /**
   * 输入参与次数
   */
  bindCountInput: function (e) {
    let lottery = this.data.lottery
    lottery.count = Number(e.detail.value)
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

  /**
   * 输入奖品总数
   */
  bindTotalInput: function (e) {
    let data = e.currentTarget.dataset
    let index = data.index
    let lottery = this.data.lottery
    let prize = lottery.prizeList[index]
    prize.total = Number(e.detail.value)
    lottery.prizeList[index] = prize
    this.setData({
      lottery: lottery
    })
  },

  /**
   * 校验奖品总数
   */
  bindTotalValid: function (e) {
    let rx = new RegExp('^[1-9][0-9]{0，3}$')
    console.log(e.detail.value)
    console.log(rx.test(e.detail.value))
    if (rx.test(e.detail.value)) {
      console.log('有效手机号')
    } else {
      console.log('手机号无效')
    }
  },

  /**
   * 输入万次抽中
   */
  bindWeightInput: function (e) {
    let data = e.currentTarget.dataset
    let index = data.index
    let lottery = this.data.lottery
    let prize = lottery.prizeList[index]
    prize.weight = Number(e.detail.value)
    lottery.prizeList[index] = prize
    this.setData({
      lottery: lottery
    })
  },

  /**
   * 校验万次抽中
   */
  bindWeightValid: function (e) {
    let rx = new RegExp('^[1-9][0-9]{0，3}$')
    console.log(e.detail.value)
    console.log(rx.test(e.detail.value))
    if (rx.test(e.detail.value)) {
      console.log('有效手机号')
    } else {
      console.log('手机号无效')
    }
  },

  bindNameInput: function (e) {
    let data = e.currentTarget.dataset
    let index = data.index
    let lottery = this.data.lottery
    let prize = lottery.prizeList[index]
    prize.name = e.detail.value
    lottery.prizeList[index] = prize
    this.setData({
      lottery: lottery
    })
  },

  bindSubmitTap: function () {
    let lottery = this.data.lottery
    lottery.openid = app.globalData.userInfo.openid
    app.ajaxPost({
      url: '/lottery/'+(this.data.createLottery?'add':'update'),
      data: lottery,
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
  },

  bindAddPrizeTap: function(e) {
    let lottery = this.data.lottery
    if (lottery.prizeList==undefined){
      lottery.prizeList = []
    }
    let prizeList = lottery.prizeList
    prizeList.push({
      name: '',
      total: 0,
      weight: 0,
      quantity: 0
    })
    lottery.prizeList = prizeList
    this.setData({
      lottery: lottery
    })
  },

  bindDeletPrizeTap: function (e) {
    let data = e.currentTarget.dataset
    let index = data.index
    let lottery = this.data.lottery
    lottery.prizeList.splice(index, 1)
    this.setData({
      lottery: lottery
    })
  }
})