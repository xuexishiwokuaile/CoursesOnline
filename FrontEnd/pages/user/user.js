// pages/user/user.js
//作者：周莲
var app=getApp()
Page({
  userInfo:[],
  name:'',
  url:'',
  onLoad:function(options){
    console.log(app.globalData.user.userInfo)
    // 页面初始化 options为页面跳转所带来的参数
    var that = this
    that.setData({
      userInfo:app.globalData.user.userInfo,
      name: app.globalData.user.userInfo.nickName,
      url: app.globalData.user.userInfo.avatarUrl,
    })
    
 
  },
  kaifazhe:function()
  {
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/admin/vertifyAdmin',
      data: { 
        openid: openid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log(res.data[0].flag)
        console.log('success')
        if(res.data[0].flag)
        {
          wx.navigateTo({
            url: '/pages/manager/manager'
          })
        }
        else{
          wx.showToast({
            title: '您不是管理员',
            icon: 'none'
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
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  }
})