//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    hasNickName: false,
    carousels: [],
    lotteryCustomers: [],
    lotteryCustomerPage: 1,
    lotteryCustomerPages: 0,
    acode: '',
    showAcode: false,
    interval: undefined
  },
  //事件处理函数
  bindRetailerTap: function() {
    if(this.data.hasNickName){
      wx.navigateTo({
        url: '../retailer/home'
      })
    }else{

    }
  },
  bindScanTap: function(){
    // wx.scanCode({
    //   success: res => {
    //     console.log(res)
    //   }
    // })
    wx.navigateTo({
      url: '../lottery/lottery?lotteryId=1'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        hasNickName: app.globalData.userInfo.nickName!=null
      })
      this.getLotteryCustomerData()
    } else {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
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
      data: { prize: true, customerId: app.globalData.userInfo.id, sort: 'lp.lotteryId', order: 'desc', page: this.data.lotteryCustomerPage },
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
    let self = this
    this.setData({
      acode: app.globalData.service + '/wx/acode?scene=' + prize.id +'&v='+Math.random(),
      showAcode: true,
      interval: setInterval(function(){
        app.ajaxGet({
          url: '/lottery/customer/get/'+prize.id,
          success: res => {
            if(res.success){
              if (res.data.status =='exchanged'){
                let lotteryCustomers = self.data.lotteryCustomers
                let lotteryCustomer = {}
                for (lotteryCustomer in lotteryCustomers){
                  if (lotteryCustomer.lotteryId == prize.lotteryId){
                    let p = {}
                    for (p in lotteryCustomer.list){
                      if(p.id == prize.id){
                        p.status = res.data.status
                        break;
                      }
                    }
                    break;
                  }
                }
                self.setData({
                  showAcode: false,
                  interval: null,
                  lotteryCustomers: lotteryCustomers
                })
                wx.showToast({
                  title: '您成功兑换了'+prize.name,
                  icon: 'none',
                  duration: 1000
                })
              }
            }
          },
          fail: res => {
            self.setData({
              showAcode: false,
              interval: null
            })
            wx.showToast({
              title: '出问题了，请稍后再试',
              icon: 'none',
              duration: 1000
            })
          }
        })
      },2000)
    })
  },
  onShareAppMessage: function(options){
    if (options.from == 'button'){
      let data = options.target.dataset
      let prize = data.prize
      return {
        title: prize.store+'做活动啦！我领到了'+prize.name+"，你也来试试吧！",
        path: '/pages/lottery/lottery?lotteryId='+prize.lotteryId,
        imageUrl: ''
      }
    }else{
      return {
        title: '助销小工具助力商家快速营销'
      }
    }
  }
})
