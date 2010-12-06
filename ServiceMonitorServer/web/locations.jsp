<%@page contentType="text/html" pageEncoding="UTF-8" import="servicemonitor.model.*,java.util.*,servicemonitor.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User locations</title>

        <script type="text/javascript" src="http://www.google.com/jsapi"></script>
		<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=ABQIAAAADS3ReqaK6ybKZkaCS6IPnBQo_j_zzW7uWOumluv7fXmdq-YJNxR60C9JG1ePx3Aq0k6zXLKsu7uoNQ" type="text/javascript"></script>
        <script type="text/javascript">
            google.load("visualization", "1", {packages:["map"]});
            google.setOnLoadCallback(drawMap);
            function drawMap() {
                var data = new google.visualization.DataTable();
                data.addColumn('number', 'Lat');
                data.addColumn('number', 'Lon');
                data.addColumn('string', 'Name');
                data.addRows(4);

                <%
                System.out.println("locations.jsp starting...");

                ressipoller.dashboard.Dashboard db = new ressipoller.dashboard.Dashboard();
                db.connect();

                ressipoller.dashboard.LocationDataset lds = db.getUserLocations(new java.util.Date());
                out.println("data.addRows(" + lds.getIds().size() + ");");

                for (int i = 0; i < lds.getIds().size(); i++) {
                    out.println("data.setCell(" + i + ", 0, " + lds.getLatitudes().get(i) + ");");
                    out.println("data.setCell(" + i + ", 1, " + lds.getLongitudes().get(i) + ");");
                    out.println("data.setCell(" + i + ", 2, '" + lds.getIds().get(i) + "');");
                }

                db.disconnect();
                System.out.println("locations.jsp done...");

                %>

                var map = new google.visualization.Map(document.getElementById('map_div'));
                map.draw(data, {showTip: true});
            }
        </script>
    </head>
    <body>
        <div id="map_div" style="width: 400px; height: 250px;"></div>
    </body>
</html>
