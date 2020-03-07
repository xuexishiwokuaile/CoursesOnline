// pages/guancomment/guancomment.js
//作者：周莲
Page({

  /**
   * 页面的初始数据
   */
  data: {
    comments:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/commentAudit',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          comments: res.data
        })
        console.log(res.data)
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
  pass:function(e)
  {
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/passCommentAudit',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data:{
        commentId:e.currentTarget.id
      },
      success: function (res) {
        that.setData({
          comments: res.data
        })
        console.log(res.data)
        console.log('success')
        that.onLoad()
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

  },
  reject:function(e)
  {
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/rejectCommentAudit',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        commentId: e.currentTarget.id
      },
      success: function (res) {
        that.setData({
          comments: res.data
        })
        console.log(res.data)
        console.log('success')
        that.onLoad()
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