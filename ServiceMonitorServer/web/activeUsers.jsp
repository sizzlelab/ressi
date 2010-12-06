<%-- 
    Document   : userActivity
    Created on : Mar 4, 2010, 1:32:04 AM
    Author     : ktuomain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="servicemonitor.model.*,java.util.*,servicemonitor.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User activity</title>

        <script type='text/javascript' src='http://www.google.com/jsapi'></script>
        <script type='text/javascript'>
            google.load('visualization', '1', {'packages':['annotatedtimeline']});
            google.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = new google.visualization.DataTable();
                data.addColumn('date', 'Date');
               
                <%

            ressipoller.dashboard.Dashboard db = new ressipoller.dashboard.Dashboard();
            db.connect();
            ressipoller.dashboard.DateTimeSeriesDataset ds = db.getActivitySum();

            for (String i : ds.getDimensions())
                out.println("data.addColumn('number', '" + i + "');");

            out.println("data.addRows([");
            for (Date date : ds.getDates()) {
                out.print("[new Date(" +  date.getTime()  + ")");

                for (String dimension : ds.getDimensions()) {
                    Double dValue = ds.getDataPoint(date, dimension);
                    String sValue = (dValue == null) ? "0" : dValue.toString();
                    
                    out.print("," + sValue);
                }
                out.println("],");
            }

            db.disconnect();

            %>


                    ]);

                    var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
                    chart.draw(data, {displayAnnotations: true, legendPosition: 'newRow', scaleType: 'maximized'});
                }
        </script>

    </head>
    <body>
        <div id="chart_div" style="width: 400px; height: 250px;"></div>
    </body>        
</html>
