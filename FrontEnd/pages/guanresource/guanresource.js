
//作者：周莲
var goodsData = require('../../utils/goodsData.js');

//获取应用实例
var app = getApp()
Page({
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isHide: false,
    carousel: [
      "/images/1.png",
      "/images/2.png",
      "/images/3.png"
    ],
    user: [],
    school: [],
    index: 0,
    isCollected: false,
    list: [],
    sslist: [],
    sslisthidden: true,
    input: '',
    text: ''
  },

  pickChange: function (e) {
    this.setData({
      index: e.detail.value
    });
    //选择学校改变list
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/resDisplay',
      data: { resField: this.data.school[this.data.index].resField },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          list: res.data
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
  input: function (e) {
    this.setData({
      input: e.detail.value
    })
    this.sear()

  },
  kuang: function (e) {
    wx.showModal({
      title: '课程时间',
      content: e.currentTarget.id,
    })

  },
  delete: function (e) {
    console.log(e.currentTarget.id)
    console.log(this.data.list)
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that=this

    wx.request({
      url: 'http://188.131.217.222:8080/admin/deleteResource',
      data: {
        resId: e.currentTarget.id
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {

        console.log('success')
        wx.showToast({
          icon: 'success',
          title: '删除成功'
        })
        that.onShow()

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

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
          that.onLoad()
          that.setData({
            sslisthidden: true
          })
        }
        else {
          if (res.data[0][0] != null) {
            console.log(res.data[0][0].Text)
            console.log('对的')
            that.setData({
              text: res.data[0][0].Text + res.data[0][0].CorrectString,
            })
          }
          var list = res.data[1]
        
       
          that.setData({
            sslist: list,
            sslisthidden: false
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
  onShow:function(){
    //显示学校类型
var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/fieldDisplay',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          school: res.data
        })
        console.log('success')

        console.log(res.data)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    });
    //显示资源列表
    wx.request({
      url: 'http://188.131.217.222:8080/admin/resDisplay',
      data: { resField: '历史' },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success!')
        console.log(res.data)
        var list = res.data
      
        that.setData({
          list: list
        })
        console.log(that.data.list)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    });

 
  },
  onLoad: function (options) {
    //显示学校类型
var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/fieldDisplay',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        that.setData({
          school: res.data
        })
        console.log('success')

        console.log(res.data)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    });
    //显示资源列表
    wx.request({
      url: 'http://188.131.217.222:8080/admin/resDisplay',
      data: { resField: '历史' },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success!')
        console.log(res.data)
        var list = res.data
        that.setData({
          list: list
        })
        console.log(that.data.list)
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    });

  },
  bindGetUserInfo: function (e) {
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      var that = this;
      var user = wx.getStorageSync('user');
      var openid = user.openid;
      var avatarUrl = e.detail.userInfo.avatarUrl;


      // 获取到用户的信息了，打印到控制台上看下
      console.log("用户的信息如下：");
      console.log(e.detail.userInfo);

      app.globalData.user = e.detail.userInfo;
      console.log(app.globalData.user)
      wx.request({
        url: 'http://188.131.217.222:8080/System/LoginTest',
        data: {
          nickName: e.detail.userInfo.nickName,//uid:用户id
          openid: openid,
          avatarUrl: avatarUrl

        },
        method: 'GET',
        header: { 'content-type': 'application/json' },
        success: function (res) {
          console.log('success');
          console.log(res)
        },
        fail: function (res) {
          console.log('fail');
        },
        complete: function (res) {
          console.log('complete');
        }

      })



      //授权成功后,通过改变 isHide 的值，让实现页面显示出来，把授权页面隐藏起来
      that.setData({

        isHide: false
      });
    } else {
      //用户按了拒绝按钮
      wx.showModal({
        title: '警告',
        content: '您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
        showCancel: false,
        confirmText: '返回授权',
        success: function (res) {
          // 用户没有授权成功，不需要改变 isHide 的值
          if (res.confirm) {
            console.log('用户点击了“返回授权”');
          }
        }
      });
    }
  }
})


