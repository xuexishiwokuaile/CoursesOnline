
//作者：周莲
//获取应用实例
var app = getApp()
Page({
  data: {
    stars: [0, 1, 2, 3, 4],
    normalSrc: '../../images/normal.png',
    selectedSrc: '../../images/selected.png',
    halfSrc: '../../images/half.png',
    key: 0,//评分
    resId: ''
  },
  onLoad: function (options) {
    var that = this
    that.setData({
      resId: options.resId
    })
  },
  //点击右边,半颗星
  selectLeft: function (e) {
    var key = e.currentTarget.dataset.key
    if (this.data.key == 0.5 && e.currentTarget.dataset.key == 0.5) {
      //只有一颗星的时候,再次点击,变为0颗
      key = 0;
    }
    console.log("得" + key + "分")
    this.setData({
      key: key
    })

  },
  submit: function (e) {
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/resourceScore',
      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        openid: openid,
        resId: that.data.resId,
        score: that.data.key

      },
      success: function (res) {
        console.log('success');
        that.setData({
          comments: res.data
        })
        wx.navigateBack({
          delt: 1
        })


      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

  },
  //点击左边,整颗星
  selectRight: function (e) {
    var key = e.currentTarget.dataset.key
    console.log("得" + key + "分")
    this.setData({
      key: key
    })
  }
})

