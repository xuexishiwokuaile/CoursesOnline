// pages/guantianke/guantianke.js
//作者：周莲
Page({

  /**
   * 页面的初始数据
   */
  data: {
    coursename:'',
    courseteacher:'',
    courselocation:'',
    coursetime:'',
    courseuniversity:'',
    coursefield:''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  input1:function(e)
  {
    var that= this
    that.setData({
      coursename:e.detail.value
    })
  },
  input2: function (e) {
    var that = this
    that.setData({
      coursename: e.detail.value
    })
  },
  input3: function (e) {
    var that = this
    that.setData({
      coursename: e.detail.value
    })
  },
  input4: function (e) {
    var that = this
    that.setData({
      coursename: e.detail.value
    })
  },
  input5: function (e) {
    var that = this
    that.setData({
      courseuniversity: e.detail.value
    })
  },
  input6: function (e) {
    var that = this
    that.setData({
      coursefield: e.detail.value
    })
  },

  submit: function()
  {
    var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/addCourse',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data:{
        Cname:that.data.coursename,
        Cteacher:that.data.courseteacher,
        location:that.data.courselocation,
        University:that.data.courseuniversity,
        couField:that.data.coursefield,
        courseTime:that.data.coursetime
      },
      success: function (res) {
        console.log('success草泥马')
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    });
    wx.navigateBack({

      delta: 1  // 返回上一级页面。

    }) 

  },
  onLoad: function (options) {

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