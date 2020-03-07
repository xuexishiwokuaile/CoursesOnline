// pages/study.
//作者：周莲
var util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resId:0,
    start:'',
    list:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    var that=this
    var id=options.resid
    console.log(options.resid)

    that.setData({
      resId:id
    })
    console.log(that.data.resId)
    wx.request({
      url: 'http://188.131.217.222:8080/system/displayResFile',
      data: {
        resId:that.data.resId,
       
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {

        console.log('success')
        console.log(res.data)
        that.setData({
          list:res.data
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that=this
    var time = util.formatTime2(new Date());
    that.setData({
      start:time
    })

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onUnload: function () {
    var date = util.formatTime(new Date());
    var time = util.formatTime2(new Date());
    console.log(time)
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/system/recordHistory',
      data: { 
        openid: openid,
        resId:that.data.resId,
        inTime:that.data.start,
        date:date,
        outTime:time
       },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log(res.data)
        console.log('success')
        console.log(that.data.resId)
        console.log(that.data.start)
        console.log(date)
        console.log(time)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })


  },

  /**
   * 生命周期函数--监听页面卸载
   */


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

  }
})