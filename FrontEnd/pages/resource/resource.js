// pages/new/new.js
//作者：周莲
Page({
  data: {
    array: [],
    index: 0,
    input: '',
    isshow: false,
    open: false,
    list: [],
    recolist: [
      { name: 'sql如何连接', id: '1', info: '这个课不错' },
      { name: 'sql如何连接', id: '1', info: '这个课不错' },
      { name: 'sql如何连接', id: '1', info: '这个课不错' },
      { name: 'sql如何连接', id: '1', info: '这个课不错' },
    ],
  },

  pickChange: function (e) {
    this.setData({
      index: e.detail.value
    });
    console.log(this.data.array[this.data.index])
    //重置资源列表
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/system/resDisplay',
      data: { resField: this.data.array[this.data.index].resField },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success')
        that.setData({
          list: res.data
        })
        console.log(res.data)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
  },

  input: function (e) {
    this.setData({
      input: e.detail.value
    })
    if (this.data.input == " ") {
      this.setData({
        list: olist
      })
    }
  },
  sear: function (e) {
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/user/searchCourse',
      data: {
        keyword: this.data.input
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log(res.data)
        if (res.data == '0') {
          console.log('输入为空')
        }
        else {
          that.setData({
            list: res.data
          })
        }
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    //显示资源列表
    var that = this

    wx.request({
      url: 'http://188.131.217.222:8080/system/resDisplay',
      data: { resField: '历史' },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('成功')
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
    //显示推荐资源列表
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/getPersonalRecommend',
      data: {
        openid: openid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          recolist: res.data
        })
        console.log(res.data)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
    //显示课程类型
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/system/fieldDisplay',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          array: res.data
        })
        console.log(res.data)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})