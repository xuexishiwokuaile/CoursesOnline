// pages/guantianzi/guantianzi.js
//作者：周莲
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:'',
    field:'',
    info:'',

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

input1:function(e){
  var that=this
  that.setData({
    name:e.detail.value
  })

},
input2:function(e){
  var that = this
  that.setData({
    field: e.detail.value
  })


},
input3:function(e){
  var that = this
  that.setData({
    info: e.detail.value
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

              that.information()
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
    wx.request({
      url: 'http://188.131.217.222:8080/user/fileInfo',
      data: {
        chapter: that.data.name
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
  submit:function()
  {
    var that = this
    wx.request({
      url: 'http://188.131.217.222:8080/admin/addResource',

      method: 'GET',
      header: { 'content-type': 'application/json' },
      data: {
        Cname: that.data.name,
        resField: that.data.field,
        resInfo: that.data.info,
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