// pages/guancomment/guancomment.js
//作者：周莲
Page({

  /**
   * 页面的初始数据
   */
  data: {
    files: []

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/displayOwnFile',
      method: 'GET',
      header: { 'content-type': 'application/json' },
      data:{
        openid:openid

      },
      success: function (res) {
        that.setData({
          files: res.data
        })
        console.log(res.data)
        console.log(that.data.files)
        console.log('success')
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

  },

  reject: function (e) {
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/deleteOwnFile',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        file_id: e.currentTarget.id,
        openid: openid
      },
      success: function (res) {
        that.setData({
          comments: res.data
        })
        console.log(res.data)
        console.log('success')
        that.onLoad()
        wx.showToast({
          title: '删除成功',
          icon: 'success',
        })
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
    that.onShow()


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
      url: 'http://188.131.217.222:8080/user/displayOwnFile',
      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        openid: openid

      },
      success: function (res) {
        that.setData({
          files: res.data
        })
        console.log(res.data)
        console.log(that.data.files)
        console.log('success')
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