var app = getApp();
Page({
  data: {
    imageslist: [
      '../images/chuizi.png',
      '../images/hongbao.png'
    ],
    shareId: '',
    awardsList: {},
    animationData: {},
    btnDisabled: "",
    awardsLen: 0,
    showModal: false,
    count: 0,
    lotteryId: null,
    userInfo: {},
    hasUserInfo: false,
    lottery: {},
    runDegs: 0,
    prize: {}
  },
  onLoad: function (options) {
    var scene = decodeURIComponent(options.scene)   // 扫码进入
    
    this.setData({
      lotteryId: scene =='undefined'?'':scene
    })

    if (options.shareId && options.lotteryId) {   // 转发进入
      this.setData({
        shareId: options.shareId,
        lotteryId: options.lotteryId
      })  
    }

    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      this.getLotteryData()
    } else {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.data,
          hasUserInfo: true
        })
        this.getLotteryData()
      }
    }
  },
  initAdards: function() {
    var e = this, n = e.data.lottery.prizeList, t = n.length, i = 360 / t, s = i - 90, r = [], d = 1 / t;
    var colorArr = ['#EE534F',
      '#FF7F50',
      '#FFC928',
      '#66BB6A',
      '#42A5F6',
      '#5C6BC0',
      '#AA47BC',
      '#EC407A',
      '#FFB6C1',
      '#FFA827',
      '#EE534F',
      '#FF7F50',
      '#FFC928',
      '#66BB6A',
      '#42A5F6',
      '#5C6BC0',
      '#AA47BC',
      '#EC407A',
      '#FFB6C1',
      '#FFA827']
      e.setData({
        btnDisabled: e.data.lottery.count ? "" : "disabled"
      });
      // wx.createCanvasContext("lotteryCanvas");
      for (var o = 942.47778 / t, g = 0; g < t; g++) {
        var l = colorArr[g]
        console.log(l)
          // var l = "rgba(255,203,30,0.5)";
          // if (t % 2 == 0) l = 1 == (u = g % 2) ? "rgba(228,55,14,0.5)" : 2 == u ? "rgba(228,155,14,0.5)" : "rgba(255,203,30,0.5)"; else {
          //     var u = g % 2;
          //     l = g == t - 1 ? "rgba(228,155,14,0.5)" : 1 == u ? "rgba(228,55,14,0.5)" : "rgba(255,203,30,0.5)";
          // }

          r.push({
              k: g,
              itemWidth: o + "px",
              item2BgColor: l,
              item2Deg: g * i + 90 - i / 2 + "deg",
              item2Turn: g * d + d / 2 + "turn",
              ttt: "",
              tttSkewX: 4 * t + "deg",
              afterDeg: s + "deg",
              turn: g * d + "turn",
              lineTurn: g * d + d / 2 + "turn",
              award: n[g].name
          });
      }
      e.setData({
          awardsLen: r.length,
          awardsList: r
      });
  },
  getLotteryData: function(){
    app.ajaxGet({
      url: '/lottery/info',
      data: {
        lotteryId: this.data.lotteryId,
        customerId: this.data.userInfo.id
      },
      success: res => {
        if(res.success){
          let lottery = res.data
          let weight = 0
          let i = 0
          for (; i < lottery.prizeList.length; i ++){
            weight += lottery.prizeList[i].weight
          }
          if(weight<10000){
            lottery.prizeList.push({name: '谢谢惠顾'})
          }
          this.setData({
            lottery: lottery,
            count: lottery.count
          })
          this.initAdards()
        }
      }
    })
  },
  handleLottery: function() {
    let data = {
      customerId: this.data.userInfo.id,
      lotteryId: this.data.lotteryId
    }
    if (this.data.shareId != null && this.data.shareId!=''){
      data.shareId = this.data.shareId
    }
    app.ajaxPost({
      url: '/lottery/customer',
      data: data,
      success: res => {
        if(res.success){
          let i = 0
          if(res.data!=407){
            for (; i < this.data.lottery.prizeList.length; i ++){
              if (this.data.lottery.prizeList[i].id==res.data.id){
                break
              }
            }
            this.setData({
              prize: res.data
            })
          }else{
            i = this.data.lottery.prizeList.length - 1
            this.setData({
              prize: null
            })
          }
          let runDegs = this.data.runDegs
          console.log(runDegs)
          runDegs = runDegs + (360 - runDegs % 360) + (2160 - i * (360 / this.data.lottery.prizeList.length))
          console.log(runDegs)
          let s = wx.createAnimation({
            duration: 4e3,
            timingFunction: "ease"
          });
          this.data.animationRun = s
          s.rotate(runDegs).step()
          this.setData({
            animationData: s.export(),
            btnDisabled: "disabled",
            runDegs: runDegs
          })
          var self = this
          setTimeout(function () {
            if (self.data.prize==null){
              wx.showModal({
                title: '很遗憾！',
                content: '很遗憾奖品溜走了' + (self.data.count > 0 ? '，再试一次' : '') +'！',
                showCancel: true
              })
            } else {
              wx.showModal({
                title: '恭喜您！',
                content: '恭喜您！您抽中了' + self.data.prize.name + '！',
                showCancel: true
              })
            }
            self.setData({
              count: self.data.count - 1
            })
            if (self.data.count){
              self.setData({
                btnDisabled: ""
              });
            }
          }, 4e3);
        }else{
          wx.showToast({
            title: res.message,
            icon: 'none',
            duration: 3500
          })
        }
      }
    })
  }
});