//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    hasNickName: false,
    carousels: [],
    lotteryCustomers: [],
    lotteryCustomerPage: 1,
    lotteryCustomerPages: 0
  },
  //事件处理函数
  bindRetailerTap: function() {
    
  },
  bindScanTap: function(){
    // wx.scanCode({
    //   success: res => {
    //     console.log(res)
    //   }
    // })
    wx.navigateTo({
      url: '../lottery/lottery?lotteryId=1',
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true,
        hasNickName: app.globalData.userInfo.nickName!=null
      })
      this.getLotteryCustomerData()
    } else {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.data,
          hasUserInfo: true,
          hasNickName: res.data.nickName != null
        })
        this.getLotteryCustomerData()
      }
    }
  },
  getUserInfo: function(e) {
    app.ajaxPost({
      url: '/customer/info',
      data: {
        customerId: this.data.userInfo.id,
        nickName: e.detail.userInfo.nickName,
        avatarUrl: e.detail.userInfo.avatarUrl
      },
      success: res => {
        if(res){
          app.globalData.userInfo.nickName = e.detail.userInfo.nickName
          app.globalData.userInfo.avatarUrl = e.detail.userInfo.avatarUrl
          this.setData({
            userInfo: app.globalData.userInfo,
            hasUserInfo: true,
            hasNickName: true
          })
        }
      }
    })
  },
  onReady: function(){
    this.getCarouselData()
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
      data: { prize: true, customerId: this.data.userInfo.id, sort: 'lp.lotteryId', order: 'desc', page: this.data.lotteryCustomerPage },
      success: res => {
        if (res.success) {
          let lotteryCustomers = this.data.lotteryCustomers
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
            lotteryCustomers: lotteryCustomers,
            lotteryCustomerPages: res.pages
          })
        }
      }
    })
  },
  scrolltolower: function(){
    if (this.data.lotteryCustomerPages > this.data.lotteryCustomerPage){
      this.setData({
        lotteryCustomerPage: this.data.lotteryCustomerPage +1
      })
      this.getLotteryCustomerData()
    }
  },
  bindExchangeTap: function(e){
    let data = e.currentTarget.dataset
    let prize = data.prize
    if (prize.status == 'exchanged' || prize.status == 'expired'){
      return false
    }
    if(prize.needShare && !prize.share){
      console.log('分享')
    }else{
      console.log('兑奖二维码')
    }
  }
})
