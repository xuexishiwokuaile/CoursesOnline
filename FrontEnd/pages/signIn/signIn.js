//作者：周莲
const app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //img_url: config.imgUrl, //图片地址

    //签到模块
    signNum: 0,  //签到数
    signState: false, //签到状态
    day: '',
    min: 1,  //默认值日期第一天1
    max: 7,  //默认值日期最后一天7
    be: 0    //默认倍数


  },

  //签到
  bindSignIn(e) {
    var time = util.formatTime(new Date());
    console.log(time)
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/checkIn',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        openid: openid,
        date: time
      },
      success: function (res) {
        that.setData({
          signNum: res.data[0].check,

        })
        wx.setStorageSync('day', time)
        wx.setStorageSync('days', res.data[0].check)
        console.log(res.data)
        console.log('success')
        that.onShow()
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

    wx.showToast({
      icon: 'success',
      title: '签到成功',
    })


    var min = e.currentTarget.dataset.min,
      max = e.currentTarget.dataset.max,
      be = e.currentTarget.dataset.be;


  },
  onShow: function () {
    var day = wx.getStorageSync("day")
    var time = util.formatTime(new Date());
    console.log(day)
    console.log(time)
    if (day == time) {
      this.setData({
        signState: true
      })
    }
    var days = wx.getStorageSync("days")
    if (days) {
      this.setData({
        signNum: days
      })
    }
    var time = util.formatTime2(new Date());
    console.log(time)
  },




})
