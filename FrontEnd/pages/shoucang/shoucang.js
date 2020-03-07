//index.js
//作者：周莲
var goodsData = require('../../utils/goodsData.js');
//获取应用实例
var app = getApp()
Page({
  input: '',
  data: {
    list: [],
    reslist:[],
    isCollected:true,
  },

  delete: function (e) {
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that = this;
   
    console.log(e.currentTarget.id)
    wx.request({
      url: 'http://188.131.217.222:8080/user/cancelCourseFavourite',
      data: {
        openid: openid,
        Cid: e.currentTarget.id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        

        console.log('success')
        
        wx.showToast({
          title: '取消收藏成功',
          icon:'success'
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
  delete2: function (e) {
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that = this;
    console.log(e.currentTarget.id)
    wx.request({
      url: 'http://188.131.217.222:8080//user/cancelResourceFavourite',
      data: {
        openid:openid,
        resId: e.currentTarget.id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {

        console.log('success')

        wx.showToast({
          title: '取消收藏成功',
          icon: 'success'
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


  clickButton: function (e) {
    wx.navigateBack({
      delta: 1
    })
  },
  onLoad: function (options) {
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that=this;
    //课程
    wx.request({
      url: 'http://188.131.217.222:8080/system/chosenCourseDisplay',
      data: {
        openid:openid,//uid:用户id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        console.log(res.data);
        that.setData({
          list:res.data
        })
        for (var i = 0; i < res.data.length; i++) {
          that.data.list[i].iscollect = true
        }
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
    //资源
    wx.request({
      url: 'http://188.131.217.222:8080//user/favourite',
      data: {
        openid: openid,//uid:用户id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        console.log(res.data)
        that.setData({
          reslist: res.data
        })
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
