// pages/message/message.js
//作者：周莲
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/system/displayUserMessage',
      data: {
        openid:openid,
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
  know:function(e){
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/user/deleteMessage',
      data: {
  
        mid:e.currentTarget.id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success')
        console.log(res.data)
        console.log(e.currentTarget.id)
        that.setData({
          list: res.data
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
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/system/displayUserMessage',
      data: {
        openid: openid,
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success')
        console.log(res.data)
        that.setData({
          list: res.data
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

  }
})