//index.js
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
      "/images/1.jpg",
      "/images/2.jpg",
      "/images/3.jpg"
    ],
   user:[],
    school: [],
    index: 0,
    isCollected:false,
    list: [],
    sslist:[],
    sslisthidden:true,
  input:'',
  text:''
  },

  pickChange: function (e) {
    this.setData({
     index: e.detail.value
    });
    //选择学校改变list
    var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/user/couDisplay',
      data: {University:this.data.school[this.data.index].University},
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
  kuang:function(e){
    wx.showModal({
      title: '课程时间',
      content: e.currentTarget.id,
    })

  },
  shoucang: function (e) {
    console.log(e.currentTarget.dataset.item.Cid)
    console.log(e.currentTarget.dataset.item.iscollect)
    var that=this
    
    if (e.currentTarget.dataset.item.iscollect)
    {
      
      console.log(this.data.list)
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    console.log(openid)
      console.log(e.currentTarget.dataset.item.iscollect)
      wx.request({
        url: 'http://188.131.217.222:8080/user/cancelCourseFavourite',
      data: { 
        openid: openid,
        Cid: e.currentTarget.dataset.item.Cid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
      
        console.log('success')
        wx.showToast({
          icon:'success',
          title:'取消收藏成功'
        })
      
      },
      fail: function (res) {
        console.log('fail');
      },
      complete: function (res) {
        console.log('complete');
      }

    })}
    else {
     
      console.log(e.currentTarget.dataset.index)
      var user = wx.getStorageSync('user');
      var openid = user.openid;
      var that = this;
      console.log(that.data.list)
      console.log(e.currentTarget.dataset.item.Cid)
      console.log(e.currentTarget.dataset.item.iscollect)
      wx.request({
        url: 'http://188.131.217.222:8080/user/clickCourseFavourite',
        data: {
          openid: openid,
          Cid: e.currentTarget.dataset.item.Cid
        },
        method: 'GET',
        header: { 'content-type': 'application/json' },
        success: function (res) {

          console.log('success')
          wx.showToast({
            title: '收藏成功',
            icon: 'success'
          })
        },
        fail: function (res) {
          console.log('fail');
        },
        complete: function (res) {
          console.log('complete');
        }

      })}
   var list= this.data.list
   console.log(this.data.list)
   this.setData({
     list:list
   })
    that.onShow()
  },

  
  sear: function (e) {
    var that=this
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    wx.request({
      url: 'http://188.131.217.222:8080/user/searchCourse',
      data:{
        keyword:this.data.input,
        openid: openid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log(res.data)
        if(res.data=="0")
        {
          console.log('输入为空')
          that.onLoad()
          that.setData({
            sslisthidden:true
          })
        }
        else{
          if(res.data[0][0]!=null)
          {
            console.log(res.data[0][0].Text)
            console.log('对的')
            that.setData({
              text: res.data[0][0].Text + res.data[0][0].CorrectString,
            })
          }
          var list = res.data[1]
          for (var i = 0; i < res.data[1].length; i++) {
         
            
            list[i].src = "/images/bookmark1.png"
         
          }
 
          
          that.setData({
            sslist: list,
            sslisthidden:false      
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
    var user = wx.getStorageSync('user');
    var openid = user.openid;
var that=this
    wx.request({
      url: 'http://188.131.217.222:8080/system/universityDisplay',

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
    //显示蹭课列表
    wx.request({
      url: 'http://188.131.217.222:8080/user/couDisplay',
      data: {
        University: '武汉大学',
        openid: openid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log('success!')
        console.log(res.data)
        var list = res.data
        for (var i = 0; i < res.data.length; i++) {
          if (i % 6 == 0) {
            list[i].src = "/images/bookmark1.png"
          } else if (i % 6 == 1) {
            list[i].src = "/images/bookmark2.png"
          } else if (i % 6 == 2) {
            list[i].src = "/images/bookmark3.png"
          } else if (i % 6 == 3) {
            list[i].src = "/images/bookmark4.png"
          } else if (i % 6 == 4) {
            list[i].src = "/images/bookmark5.png"
          } else {
            list[i].src = "/images/bookmark6.png"
          }
        }
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
    var that = this;
    // 查看是否授权
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          wx.getUserInfo({
            success: function (res) {  
              console.log(res); 

              app.globalData.user=res;
            
            }
          });
        } else {
          // 用户没有授权
          // 改变 isHide 的值，显示授权页面
          that.setData({
            isHide: true
          });
        }
      }
    });
    //显示学校类型

    wx.request({
      url: 'http://188.131.217.222:8080/system/universityDisplay',

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
    var user = wx.getStorageSync('user');
    var openid = user.openid;
    //显示蹭课列表
    wx.request({
      url: 'http://188.131.217.222:8080/user/couDisplay',
      data:{
      University:'武汉大学',
      openid:openid
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {
       console.log('success!')
       console.log(res.data)
       var list=res.data
       for(var i=0;i<res.data.length;i++)
       {
         
         if (i % 6 == 0) {
           list[i].src = "/images/bookmark1.png"
         } else if (i % 6 == 1) {
           list[i].src = "/images/bookmark2.png"
         } else if (i % 6 == 2) {
           list[i].src = "/images/bookmark3.png"
         } else if (i % 6 == 3) {
           list[i].src = "/images/bookmark4.png"
         } else if(i%6 ==4)
         {
           list[i].src = "/images/bookmark5.png"
         }else{
           list[i].src = "/images/bookmark6.png"
         }
         
         
       }
       that.setData({
         list:list
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
      console.log(openid)


      // 获取到用户的信息了，打印到控制台上看下
      console.log("用户的信息如下：");
      console.log(e.detail.userInfo);
      
      app.globalData.user=e.detail.userInfo;
      console.log(app.globalData.user.openid)
      wx.request({
        url: 'http://188.131.217.222:8080/System/LoginTest',
        data: {
          nickName:e.detail.userInfo.nickName ,//uid:用户id
          openid:openid,
          avatarUrl:avatarUrl

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
  

