import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class goa1 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter pw1 = res.getWriter();

        // Get form values
        String nm1 = req.getParameter("n1");
        String nm2 = req.getParameter("n2");
        String nm3 = req.getParameter("n3");        
        String nm4 = req.getParameter("n4");
        String nm5 = req.getParameter("n5");
        String nm6 = req.getParameter("n6");

        try {
            // Connect to DB
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "surajit");

            // Insert query
            Statement stmt = con.createStatement();
            String q1 = "INSERT INTO goa VALUES('" + nm1 + "','" + nm2 + "','" + nm3 + "','" + nm4 + "','" + nm5 + "','" + nm6 + "')";
            int x = stmt.executeUpdate(q1);

            if (x > 0) {
                // Set booking info to be displayed in JSP
                req.setAttribute("name", nm1);
                req.setAttribute("email", nm2);
                req.setAttribute("phone", nm3);
                req.setAttribute("travel_date", nm4);
                req.setAttribute("travellersno", nm5);
                req.setAttribute("requestText", nm6);

                // Forward to JSP
                RequestDispatcher rd = req.getRequestDispatcher("success.jsp");
                rd.forward(req, res);
            } else {
                pw1.println("Booking unsuccessful");
            }

            con.close();
        } catch (Exception e) {
            pw1.println("Error: " + e);
        }
    }
}
