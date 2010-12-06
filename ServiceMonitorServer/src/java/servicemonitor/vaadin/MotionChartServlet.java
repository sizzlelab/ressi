/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.vaadin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import javax.inject.Inject;
import servicemonitor.dao.*;
import servicemonitor.entity.*;


/**
 *
 * @author ktuomain
 */
@WebServlet(name="MotionChartServlet", urlPatterns={"/MotionChartServlet"})
public class MotionChartServlet extends HttpServlet {
    @Inject TimeSeriesDao tsd; // = new servicemonitor.dao.TimeSeriesDao();

    @Inject RessiGroupDao rgd;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html><head><script type='text/javascript' src='http://www.google.com/jsapi'></script><script type='text/javascript'>google.load('visualization', '1', {'packages':['motionchart']}); google.setOnLoadCallback(drawChart); function drawChart() { var data = new google.visualization.DataTable(); data.addColumn('string', 'User'); data.addColumn('date', 'Date');");


        for (String i : tsd.getVariableNames()) {
            out.println("data.addColumn('number', '" + i + "');");
        }

        out.println("data.addRows([");

        Date start = new Date(Long.parseLong(request.getParameter("start")));
        Date end = new Date(Long.parseLong(request.getParameter("end")));

        String groupName = request.getParameter("groupName");

 //       String groupName = "kaikki";
     //   RessiGroupDao rgd = new RessiGroupDao();
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

out.println("                var chart = new google.visualization.MotionChart(document.getElementById('chart_div'));");
out.println("        chart.draw(data, {width: 1000, height:600});");
out.println("      }");
out.println("    </script>");

out.println("    <title>Motion chart</title>");
out.println("    </head>");
out.println("    <body>");
out.println("        <div id='chart_div'></div>");
out.println("    </body>");
out.println("</html>");

            /*out.println("<head>");
            out.println("<title>Servlet MotionChartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MotionChartServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
