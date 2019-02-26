//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    carousels: [],
    lotteryCustomers: []
  },
  //事件处理函数
  bindRetailerTap: function() {
    if (app.globalData.userInfo.nickName != null){
      wx.navigateTo({
        url: '../retailer/home'
      })
    }else{
      wx.navigateTo({
        url: '../authorize/authorize'
      })
    }
  },
  bindPrizeTap: function () {
    wx.navigateTo({
      url: '../customer/index'
    })
  },
  bindScanTap: function(){
    wx.scanCode({
      success: res => {
        console.log(res)
      }
    })
  },
  bindCustomerLotteryTap: function(e){
    let data = e.currentTarget.dataset
    wx.navigateTo({
      url: '../customer/lottery?lotteryId=' + data.id
    })
  },
  onLoad: function (options) {
    
  },
  onReady: function(){
    
  },
  getCarouselData: function(){
    app.ajaxGet({
      url: '/carousel/page',
      data: { status: 'on'},
      success: res => {
        if(res.success){
          let carousels = []
          res.data.forEach(carousel=>{
            carousels.push(carousel)
          })
          this.setData({
            carousels: carousels
          })
        }
      }
    })
  },
  getLotteryCustomerData: function () {
    app.ajaxGet({
      url: '/lottery/customer/page',
      data: { prize: true, customerId: app.globalData.userInfo.id, sort: 'lp.lotteryId', order: 'desc', page: '1', pageSize: '5' },
      success: res => {
        if (res.success) {
          let lotteryCustomers = []
          let i = 0
          for (; i < res.data.length; i ++){
            let lotteryCustomer = res.data[i]
            let find = false
            let lci = 0
            for (; lci < lotteryCustomers.length; lci ++){
              let lc = lotteryCustomers[lci]
              if (lc.lotteryId == lotteryCustomer.lotteryId){
                lc.list.push(lotteryCustomer)
                find = true
                break;
              }
            }
            if(!find){
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
            lotteryCustomers: lotteryCustomers
          })
        }
      }
    })
  },
  onShareAppMessage: function(options){
    return {
      title: '助销小工具助力商家快速营销'
    }
  },
  onShow: function () {
    if (app.globalData.userInfo) {
      this.getLotteryCustomerData()
    } else {
      app.userInfoReadyCallback = res => {
        this.getLotteryCustomerData()
      }
    }
    this.getCarouselData()
  }
})
