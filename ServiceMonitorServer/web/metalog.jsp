<%-- 
    Document   : metalog
    Created on : Feb 10, 2010, 3:23:07 AM
    Author     : ktuomain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Metalog</title>

        <script type='text/javascript' src='http://www.google.com/jsapi'></script>
        <script type='text/javascript'>
            google.load('visualization', '1', {'packages':['annotatedtimeline']});
            google.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = new google.visualization.DataTable();
                data.addColumn('date', 'Date');
                data.addColumn('number', 'Males');
                data.addColumn('number', 'Females');
                data.addColumn('number', 'Sex unknown');
                data.addRows([
            
            <%

            ressipoller.dashboard.Dashboard db = new ressipoller.dashboard.Dashboard();
            db.connect();

            java.util.List<servicemonitor.entity.Metalog> logs = db.getMetalogs();

            for (servicemonitor.entity.Metalog i : logs) {
                out.println("[new Date(" +  i.getCreatedAt().getTime()  + ")," +
                    i.getMaleCount() + ", " + i.getFemaleCount() + ", " +
                    i.getUnknownSexCount() +
                                       "],");
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
        <div id='chart_div' style="width: 400px; height: 250px;"></div>
    </body>
</html>
