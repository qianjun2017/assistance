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
    loading: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getLotteryData()
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

  getLotteryData: function () {
    this.setData({
      loading: true
    })
    app.ajaxGet({
      url: '/lottery/page',
      data: { retailerId: app.globalData.userInfo.id, sort: 'l.createTime', order: 'desc', page: this.data.lotteryPage, pageSize: '1', status: 'over' },
      success: res => {
        if (res.success) {
          let lottery = res.data[0]
          app.ajaxGet({
            url: '/lottery/get/' + lottery.id,
            success: l => {
              if (l.success) {
                lottery.prizeList = l.data.prizeList
                let lotterys = this.data.lotterys
                lotterys.push(lottery)
                this.setData({
                  lotterys: lotterys,
                  lotteryPages: res.pages,
                  loading: false
                })
              }
            }
          })
        }
      }
    })
  },
  scrolltolower: function () {
    if(this.data.loading){
      return
    }
    if(this.data.lotterPages > this.data.lotteryPage) {
      this.setData({
        lotteryPage: this.data.lotteryPage + 1
      })
      this.getLotteryData()
    }
  }
})