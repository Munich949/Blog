<template>
  <div style="width: 800px;height: 600px;"></div>
</template>

<script>
import * as echarts from 'echarts'
import { getRequest } from '../utils/api'

export default {
  data() {
    return {
      option: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['浏览量']
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            dataView: {
              readOnly: false
            },
            magicType: {
              type: ['line', 'bar']
            },
            restore: {},
            saveAsImage: {}
          }
        },
        series: [{
          name: '浏览量',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [],
          markLine: {
            data: [
              { type: 'average', name: '平均值' }
            ]
          }
        }]
      }
    }
  },
  mounted() {
    getRequest('/article/dataStatistics')
      .then(resp => {
        if (resp.status === 200) {
          this.option.xAxis.data = resp.data.categories;
          this.option.series[0].data = resp.data.ds;

          this.initChart();
        } else {
          this.$message({ type: 'error', message: '数据加载失败!' });
        }
      })
      .catch(error => {
        this.$message({ type: 'error', message: '数据加载失败!' });
        console.error(error);
      });
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el);
      this.chart.setOption(this.option);
    }
  }
}
</script>
