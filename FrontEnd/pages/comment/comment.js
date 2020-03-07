//index.js
//作者：周莲
//时间：07.06
var goodsData = require('../../utils/goodsData.js');
//获取应用实例
var app = getApp()
Page({
  input: '',
  data: {
    list: [],
    tuijianlist: [],
  },

  comment: function (e) {
    wx.navigateTo({
      url:'/pages/Tocomment/Tocomment'
    })
  },

  shoucang2: function (e) {
    var current = e.currentTarget.dataset.index;
    var list = this.data.tuijianlist;
    list.splice(current, 1);
    this.setData({
      tuijianlist: list,
    })
  },
  clickButton: function (e) {
    wx.navigateBack({
      delta: 1
    })
  },
  onShow:function(){
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/system/displayHistory',
      data: {
        openid: openid,
      },
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
  onLoad: function (options) {
    var that=this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/system/displayHistory',
      data: {
        openid: openid,
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success')
        that.setData({
          list:res.data
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

  }
})
