<%-- 
    Document   : motionChart
    Created on : May 16, 2010, 4:12:27 PM
    Author     : ktuomain
--%>

<%@page contentType="text/html" import="java.util.*,servicemonitor.dao.*,servicemonitor.entity.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

            <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('visualization', '1', {'packages':['motionchart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'User');
        data.addColumn('date', 'Date');

<%
        servicemonitor.dao.TimeSeriesDao tsd = new servicemonitor.dao.TimeSeriesDao();

        for (String i : tsd.getVariableNames()) {
            out.println("data.addColumn('number', '" + i + "');");
        }

        out.println("data.addRows([");

        Date start = new Date(Long.parseLong(request.getParameter("start")));
        Date end = new Date(Long.parseLong(request.getParameter("end")));

        String groupName = request.getParameter("groupName");
        
 //       String groupName = "kaikki";
        RessiGroupDao rgd = new RessiGroupDao();
        RessiGroup group = rgd.getOrCreateGroup(groupName);
      
        TimeSeriesDao.TimeSeriesData timeSeries = tsd.getSeries(group, start, end, tsd.getVariableNames());
        SortedMap<Date, List<TimeSeriesDao.UserDayData>> tsData = timeSeries.getTimeSeries();

        for (java.util.Date date : timeSeries.getTimeSeries().keySet()) {
            List<TimeSeriesDao.UserDayData> list = tsData.get(date);

//            out.println(date.toString());
            for (int i = 0; i < timeSeries.getUserNames().size(); i++) {
                String userName = timeSeries.getUserNames().get(i);
                out.print("['" + userName + "', new Date(" + date.getTime() + ")");

                TimeSeriesDao.UserDayData udd = list.get(i);
                for (Double value : udd.getValues()) {
                    out.print(", " + value);
                }

                out.println("],");
            }
        }

        out.println("]);");
%>

        var chart = new google.visualization.MotionChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 1000, height:600});
      }
    </script>
            
    <title>Motion chart</title>
    </head>
    <body>
        <div id="chart_div"></div>
    </body>
</html>
