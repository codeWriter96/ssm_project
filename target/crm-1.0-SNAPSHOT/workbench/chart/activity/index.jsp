<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basepath = request.getScheme() + "://"
            + request.getServerName() +
            ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basepath%>">
    <title>Title</title>
    <script src="ECharts/echarts.min.js"></script>
    <script src="jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            getChars();
        })
        function getChars() {
            $.ajax({
                url:"workbench/activity/getCounts.do",
                type:"get",
                dataType:"json",
                success:function (data) {

                    var myChart = echarts.init(document.getElementById('main'));
                    var option = {
                            title: {
                                text: '市场活动列表图',
                                subtext: '总数量：'+data.total+''
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c}%"
                            },

                            legend: {
                                data: data.list
                            },

                            series: [
                                {
                                    name:'漏斗图',
                                    type:'funnel',
                                    left: '10%',
                                    top: 60,
                                    //x2: 80,
                                    bottom: 60,
                                    width: '80%',
                                    // height: {totalHeight} - y - y2,
                                    min: 0,
                                    max: data.total,
                                    minSize: '0%',
                                    maxSize: '100%',
                                    sort: 'descending',
                                    gap: 2,
                                    label: {
                                        show: true,
                                        position: 'inside'
                                    },
                                    labelLine: {
                                        length: 10,
                                        lineStyle: {
                                            width: 1,
                                            type: 'solid'
                                        }
                                    },
                                    itemStyle: {
                                        borderColor: '#fff',
                                        borderWidth: 1
                                    },
                                    emphasis: {
                                        label: {
                                            fontSize: 20
                                        }
                                    },
                                    data: data.list
                                }
                            ]
                        };
                        myChart.setOption(option);
                    }
            })
        }

    </script>
</head>
<body>
<center>
    <div id="main" style="width: 800px;height:400px;"></div>
</center>

</body>
</html>
