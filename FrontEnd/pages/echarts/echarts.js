import * as echarts from '../../ec-canvas/echarts';



let chart = null;
let datelist = [];
let fieldlist = [/*"历史","农林园艺","计算机","理学","综合类","外语","经济学","管理学","国家精品","哲学"*/];
let myseries = [];
let timelistx =[];
function initChart(canvas, width, height) {
  chart = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(chart);

  var option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {            // 坐标轴指示器，坐标轴触发有效
        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    legend: {
      data: fieldlist
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value'
    },
    yAxis: {
      type: 'category',
      data: datelist
    },
    series:myseries
    };

    chart.setOption(option,true);
    return chart;
};

Page({
  onShareAppMessage: function (res) {
    return {
      title: 'ECharts 可以在微信小程序中使用啦！',
      path: '/pages/index/index',
      success: function () { },
      fail: function () { }
    }
  },
  data: {
    ec: {
      onInit: initChart
    },
    
  },

  onReady:function() {
    // setTimeout(function () {
    //   // 获取 chart 实例的方式
    //   console.log(chart)
    // }, 2000);
  },
  // onLoad:function(options)
  // {
  //   console.log('hhh')
  //   var that = this; 
  //   wx.request({
  //     url: 'http://192.168.153.210:8080/user/getEchart',
  //     data: {
  //       openid: 'o6duV5N2QJrt7wV2pjmfrEiCwBRQ',
  //     },
  //     method: 'GET',
  //     header: { 'content-type': 'application/json' },
  //     success: function (res) {

  //       console.log('success');
  //       console.log(res.data);
  //       for(var i=0;i<res.data.length;i++)
  //       {
  //         datelist.push(res.data[i][0].date);
  //       }

  //       for(var i =0;i<res.data[0].length;i++)
  //       {
  //         fieldlist.push(res.data[0][i].field);
  //       }
        
    //     for (var i = 0; i < res.data[0].length; i++)
    //     {
    //       var timelisty = new Array();
    //       for (var j = 0; j < res.data.length; j++) {
    //         timelisty.push(res.data[j][i].time);
    //       }
    //       timelistx.push(timelisty);
          
    //     }

    //     for(var i=0;i<res.data.length;i++)
    //     {
    //       var serie =
    //       {
    //         name: fieldlist[i],
    //         type: 'bar',
    //         stack: '总量',
    //         label: {
    //           normal: {
    //             show: true,
    //             position: 'insideRight'
    //           }
    //         },
    //         data: timelistx[i],
    //       };
    //       myseries.push(serie);
    //     }
    //     console.log(myseries);

  //       chart.setOption(
  //         {
  //           legend: {
  //             data: fieldlist
  //           },
  //           yAxis: {
  //             type: 'category',
  //             data: datelist
  //           }
    
  //         }
  //       );

  //     },
  //     fail: function (res) {
  //       // console.log('fail');
  //     },
  //     complete: function (res) {
  //       // console.log('complete');
  //     }

  //   })
  // }
  // ,
  onShow:function()
  {
    console.log('hhh')
    var that = this;
    wx.request({
      url: 'http://188.131.217.222:8080/user/getEchart',
      data: {
        openid: 'o6duV5N2QJrt7wV2pjmfrEiCwBRQ',
      },
      method: 'GET',
      header: { 'content-type': 'application/json' },
      success: function (res) {

        console.log('success');
        console.log(res.data);
        for (var i = 0; i < res.data.length; i++)
        {
          datelist.push(res.data[i][0].date);
        }

        for(var i =0;i<res.data[0].length;i++)
        {
          fieldlist.push(res.data[0][i].field);
        }

        for (var i = 0; i < res.data[0].length; i++) {
          var timelisty = new Array();
          for (var j = 0; j < res.data.length; j++) {
            if(res.data[j][i].time=="0")
            {
              timelisty.push("");
            }
            else{
              timelisty.push(res.data[j][i].time);
            }
            
          }
          timelistx.push(timelisty);

        }

        for (var i = 0; i < res.data.length; i++) {
          var serie =
          {
            name: fieldlist[i],
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: timelistx[i],
          };
          myseries.push(serie);
        }
        // console.log(myseries);
        var option = {
          // legend: {
          //   data: fieldlist
          // },
          // yAxis: {
          //   type: 'category',
          //   data: datelist
          // },
          // series: myseries
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: fieldlist
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: datelist
          },
          series: myseries
        }

        chart.setOption(option,true);

      },
      fail: function (res) {
      },
      complete: function (res) {
      }

    })
  }
 
});
