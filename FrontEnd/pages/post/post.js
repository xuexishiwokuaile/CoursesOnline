//作者：周莲
Page({
  data: {
    img_url: [],
    content: ''
  },
  onLoad: function (options) {
  },
  input: function (e) {
    this.setData({
      content: e.detail.value
    })
  },
  chooseimage: function () {
    var that = this;
    wx.chooseImage({
      count: 9, // 默认9 
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有 
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有 
      success: function (res) {
        if (res.tempFilePaths.length > 0) {
          //图如果满了9张，不显示加图
          if (res.tempFilePaths.length == 9) {
            that.setData({
              hideAdd: 1
            })
          } else {
            that.setData({
              hideAdd: 0
            })
          }
          //把每次选择的图push进数组
          let img_url = that.data.img_url;
          for (let i = 0; i < res.tempFilePaths.length; i++) {
            img_url.push(res.tempFilePaths[i])
          }
          that.setData({
            img_url: img_url
          })
        }
      }
    })
    
  },
  //发布按钮事件
 
  //图片上传
  img_upload: function () {
    console.log('1')
    let that = this;
    let img_url = that.data.img_url;
    let img_url_ok = [];
    //由于图片只能一张一张地上传，所以用循环
       for (let i = 0; i < img_url.length; i++) {
      wx.uploadFile({
        //路径填你上传图片方法的地址
        url: 'http://188.131.217.222:8080/oss/fileObj',//后台接口
        header: { "Content-Type": 'application/json' },//类型
        filePath: img_url[i],
        method: 'GET',

        name: 'myfile',//文件名
        success: function (res) {
          console.log('上传成功');
          console.log(res.data)
               
          }
      })
        }
     
  },
  
   text:function()
   {
   console.log('2')
     var that = this
     var user = wx.getStorageSync('user');
     var openid = user.openid;
     wx.request({
       url: 'http://188.131.217.222:8080/user/getMomentInfo',
       data: {
         openid: openid,
         content:that.data.content
       },
       method: 'GET',
       header: { 'content-type': 'application/json' },
       success: function (res) {
         console.log('success')
         let pages = getCurrentPages(); //获取当前页面js里面的pages里的所有信息。

         let prevPage = pages[pages.length - 2];

         //prevPage 是获取上一个页面的js里面的pages的所有信息。 -2 是上一个页面，-3是上上个页面以此类推。

         prevPage.setData({  // 将我们想要传递的参数在这里直接setData。上个页面就会执行这里的操作。

           url: that.data.url
         })

         wx.navigateBack({

           delta: 1  // 返回上一级页面。

         }) 
             },
       fail: function (res) {
         console.log('fail');
       },
       complete: function (res) {
         console.log('complete');
       }

     })
   }  , 
   
  send: function () {
    var that = this;
  
   
    that.text()

  },
  ok:function(){
    var that=this
    that.img_upload()
  }

})