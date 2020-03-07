//answer.js
//作者：周莲
var util = require('../../utils/util.js')

var app = getApp()
Page({
  data: {
    text:'收藏',
    course:'',
    name:'',
    info:'',
    show:true,
    score:'',
    ellipsis: true,
    chapter:[],
    comments: []

  },
  //事件处理函数
  bindItemTap: function () {
    wx.navigateTo({
      url: '../answer/answer'
    })
  },
 ellipsis: function() {
    var value = !this.data.ellipsis;
    this.setData({
      ellipsis: value
    })
  }
  ,
  shoucang:function(e){
    this.setData({
      text:'已收藏'
    })
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/user/clickResourceFavourite',
      data: {
        openid: openid,
        resId: that.data.course
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {

        console.log('success')
        console.log(that.data.course)
        wx.showToast({
          title: '收藏成功',
          icon:'success'
        })
        that.setData({
          iscollect:true
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
  comment: function (e) {
    wx.navigateTo({
      url: '/pages/Tocomment/Tocomment?resId='+this.data.course
    })
  },
  score:function(e){
    wx.navigateTo({
      url: '/pages/Toscore/Toscore?resId=' + this.data.course
    })

  },
  onShow:function(){
    var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/system/comment',
      data: { resId: this.data.course },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        that.setData({
          comments: res.data
        })
        console.log(that.data.comments)

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
    wx.request({
      url: 'http://188.131.217.222:8080/system/calculateScore',
      data: { resId: that.data.course },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        var a = res.data[0].score;
        a = a.substring(0, 3);
        console.log(a)
        that.setData({
          score: a
        })
        console.log(res.data[0].score)

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
   

  },
  upvideo: function (e) {
    var that = this;

    wx.chooseVideo({
      count: 1,
      type: 'file',
      success(res) {
        var path = res.tempFilePath;//文件资源地址
        console.log(path)
        that.setData({
          src: path
        })
        wx.showModal({
          title: '提示',
          content: '确认您的选择',
          success(res) {
            if (res.confirm) {
              console.log('用户点击确定')
              that.setData({
                name: e.currentTarget.id
              })

              
              //将文件传给开发者服务器
              wx.uploadFile({
                url: 'http://188.131.217.222:8080/oss/fileObj',//后台接口
                header: { "Content-Type": 'application/json' },//类型
                filePath: path,
                method: 'GET',

                name: 'myfile',//文件名

                success(res) {
                  console.log('传输成功')
                  wx.showToast({
                    title: '传输成功',
                    icon: 'success'
                  })
                  that.information()
                }
              })
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
      }
    })
  },
  information: function (e) {
    var that = this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/fileInfo',
      data: {
        chapter: that.data.name,
        openid:openid,
        resId:that.data.course
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
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
  onLoad: function (options) {
    var that = this; 
    this.data.course = options.courseid
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/system/getResInformation',

      header: {
        "Content-Type": "application/json"
      },

      method: "GET",

      data: { resId: this.data.course,
      openid:openid},
      success: function (res) {
        console.log('success')
        console.log(that.data.course)
        console.log(res.data[0].Cname)
        console.log(res.data)
        that.setData({
          name:res.data[0].Cname,
          info:res.data[0].resInfo,
          course:res.data[0].resId,
          iscollect:res.data[0].iscollect,
          //chapter:res.data.list,
          //id:res.data.id,
      
        })

      }
    })
    wx.request({
      url: 'http://188.131.217.222:8080/system/calculateScore',
      data: { resId: that.data.course },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        var a=res.data[0].score;
        a=a.substring(0,3);
        console.log(a)
        that.setData({
          score: a
        })
        console.log(res.data[0].score)

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
    wx.request({
      url: 'http://188.131.217.222:8080/system/comment',
      data: { resId: this.data.course },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        that.setData({
          comments: res.data
        })
        console.log(that.data.comments)

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })

  },
  tapName: function (event) {
    console.log(event)
  },
  isshow:function(e){
    this.setData({
      show:!this.data.show
    })
   
    console.log(this.data.course)
    var that = this;
    wx.request({
      url: 'http://188.131.217.222:8080/system/comment',
      data: { resId: this.data.course },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success');
        that.setData({
          comments: res.data
        })
        console.log(that.data.comments)

      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })
  },

  onShareAppMessage: function () {
    return {
      title: '微信小程序',
      desc: '分享',
      path: '/pages/detail/detail?id=123'
    }
  }

})
