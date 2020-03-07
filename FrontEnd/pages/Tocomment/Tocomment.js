// pages/comment/index.js
//作者：周莲
var app = getApp();
var util = require('../../utils/util.js');
Page({
  data: {
    resid:'',
    content:''
  },
  
  bindinput: function (e) {
    this.setData({ content: e.detail.value });
  },

  submit: function () {
    
    var that = this;
    var content = that.data.content;
    console.log(content);
    console.log(this.data.resid)
    if (content == '' && ((that.data.files).length == 0)) {
      util.isError('请输入内容', that);
      return false;
    }

    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/comment',
      data: {
        content:content,
        openid:openid,
        resourceId:that.data.resid
        },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        wx.showToast({
          title: '发布成功',
          icon: 'success',
          duration: 3000
        });
        wx.navigateBack({
          delta: 1
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
  onLoad: function (options) {
    this.data.resid=options.resId
    console.log(this.data.resid)

  }
})