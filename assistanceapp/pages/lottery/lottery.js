var a = getApp();
var utils = require('../../utils/util.js')
Page({
    data: {
      imageslist: [
        '../../image/chuizi.png',
        '../../image/hongbao.png'
      ],
      forceShare: 0,
      shareId: '',
      sendShareId: '',
      awardsList: {},
      animationData: {},
      btnDisabled: "",
      actNum: "",
      awardsLen: 0,
      prize: {},
      showModal: false,
      phone: '',
      count: '',
      lotteryid: ''
    },
  onLoad: function (options) {
    var scene = decodeURIComponent(options.scene)   // 扫码进入
    
    this.setData({
      lotteryid: scene =='undefined'?'':scene
    })

    if (options.shareid && options.lotteryid) {   // 转发进入
      this.setData({
        sendShareId: options.shareid,
        lotteryid: options.lotteryid
      })  
    }
    },
    initAdards: function() {
        var e = this, n = a.awardsConfig.awards, t = n.length, i = 360 / t, s = i - 90, r = [], d = 1 / t;
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
            btnDisabled: a.awardsConfig.chance ? "" : "disabled"
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
    getLottery: function() {
      if (this.data.count <= 0) {
        wx.showModal({
          content: '抽奖次数已用光！',
          showCancel: false
        })
        return false
      }
      if (this.data.lotteryid == ''){
        wx.showToast({
          title: '未获取到商品信息',
          icon: 'none'
        })
        return false
      }
      var e = this, n = a.awardsConfig, t = n.awards.length
      n.chance = n.chance - 1;
      e.setData({
        count: e.data.count<=0?0:e.data.count-1
      })
      // 算几率
      var i = 0
      var sumWeight = 0
      var tempArr = []
      for (var k = 0; k < t; k++) {
        sumWeight += n.awards[k].weight
        tempArr.push(sumWeight)
      }
      var randomNum = Math.floor(Math.random() * sumWeight + 1)
      for (var j = 0; j < tempArr.length; j++) {
        if (randomNum <= tempArr[j]) {
          i = j
          break
        }
      }
      var tempPrize = a.awardsConfig.awards[i]
      this.setData({prize: tempPrize})
      //输入手机号
      setTimeout(function () {
        utils.httpPost(a.globalData.domainUrl + 'customer/lottery/save', { lotteryItemId: e.data.prize.id, openid: wx.getStorageSync('openid'), shareNum: e.data.sendShareId }, function (res) {
          if (res.code == 200) {
            wx.showModal({
              title: '恭喜您！',
              content: '恭喜您！获得' + e.data.prize.name + '，赶快(我的)转发给你朋友帮你确认下吧！不然奖品无法使用哦！',
              showCancel: true
            })
          } else {
            wx.showToast({
              title: res.msg,
              icon: 'none',
              duration: 3500
            })
          }
        })
      }, 4e3)

      a.runDegs = a.runDegs || 0, a.runDegs = a.runDegs + (360 - a.runDegs % 360) + (2160 - i * (360 / t))
      var s = wx.createAnimation({
          duration: 4e3,
          timingFunction: "ease"
      });
      e.animationRun = s, s.rotate(a.runDegs).step(), e.setData({
          animationData: s.export(),
          btnDisabled: "disabled",
          // sliderDisabled: "disabled"
      }), setTimeout(function() {
          n.chance && e.setData({
              btnDisabled: "",
              // sliderDisabled: ""
          });
      }, 4e3);
    },
  onReady: function (e) {
    this.getLotteryInfo()
    },
    getLotteryInfo: function() {    // 获取转盘配置
      var self = this
      if (self.data.lotteryid == '') {
        wx.showToast({
          title: '未获取到商品信息，请扫码进入！',
          icon: 'none'
        })
        return false
      }
      utils.httpGet(a.globalData.domainUrl + 'lottery/get', { lotteryId: self.data.lotteryid},function(res){
        if (res.code == 200) {
          a.awardsConfig = {
            chance: res.data.mcount,
            awards: res.data.items,
            forceShare: res.data.forceshare
          }
          self.chance = res.data.mcount
          // 判断用户是否有资格
          utils.httpGet(a.globalData.domainUrl + 'customer/lottery/qualify', { lotteryId: self.data.lotteryid, openid: wx.getStorageSync('openid') }, function (res) {
            if (res.code == 200) {   //有资格
              if (res.data <= 0) {
                self.setData({
                  btnDisabled: 'disabled',
                })
                a.awardsConfig.chance = 0
              }
              self.setData({
                count: res.data
              })
            } else if (res.code == 501){
              wx.showToast({
                title: res.msg,
                icon: 'none',
                duration: 3000
              })
              self.setData({
                btnDisabled: 'disabled'
              })
              a.awardsConfig.chance = 0
            } else {
              utils.httpPost(a.globalData.domainUrl + 'customer/save', { openid: wx.getStorageSync('openid') }, function (res) {
                if (res.code == 200) {  //保存手机号成功，再领取奖品
                  self.setData({
                    btnDisabled: ''
                  })
                  a.awardsConfig.chance = self.chance
                } else {
                  wx.showToast({
                    title: res.msg,
                    icon: 'none',
                    duration: 2000
                  })
                  setTimeout(function () {
                    self.setData({
                      btnDisabled: 'disabled'
                    })
                    a.awardsConfig.chance = 0
                  }, 2000)
                }
              })
            }
            self.initAdards(self)
          })
        }
      })
    },
  preventTouchMove: function () {
  },
  /**
   * 隐藏模态对话框
   */
  hideModal: function () {
    this.setData({
      showModal: false
    });
  },
  /**
   * 对话框取消按钮点击事件
   */
  onCancel: function () {
    this.setData({
      btnDisabled: 'disabled'
    })
    a.awardsConfig.chance = 0
    this.hideModal();
  },
  /**
   * 对话框确认按钮点击事件
   */
  onConfirm: function () {
    var zz = /^1[34578]\d{9}$/
    var self = this
    if (zz.test(this.data.phone)) {
      this.hideModal()
      utils.httpPost(a.globalData.domainUrl + 'customer/save', { phone: this.data.phone, openid: wx.getStorageSync('openid')},function(res) {
        if (res.code == 200) {  //保存手机号成功，再领取奖品
          self.setData({
            btnDisabled: ''
          })
          a.awardsConfig.chance = self.chance
        } else {
          wx.showToast({
            title: res.msg,
            icon: 'none',
            duration: 2000
          })
          setTimeout(function () {
            self.setData({
              showModal: true
            })
            self.setData({
              btnDisabled: 'disabled'
            })
            a.awardsConfig.chance = 0
          }, 2000)
        }
      })
    } else{
      wx.showToast({
        title: '输入手机号有误！',
        icon: 'none'
      })
    }
    
  },
  inputChange: function(e) {
    this.setData({
      phone: e.detail.value
    })
  }
});