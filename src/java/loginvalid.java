import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class loginvalid extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();

        String uid = req.getParameter("l1");
        String pass = req.getParameter("l2");

        try {
            // 1️⃣ Load Oracle Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2️⃣ Connect to DB
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // 3️⃣ Prepare query (simple select, assuming reg2 table has USERNAME, EMAIL, PASSWORD)
            String q1 = "SELECT * FROM reg2 WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(q1);
            ps.setString(1, uid);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Login success
                String username = rs.getString("USERNAME");

                // Create session
                HttpSession session = req.getSession();
                session.setAttribute("username", username);

                // Redirect to homepage with URL param (so JS can hide links)
                res.sendRedirect("homepage.html?user=" + username);
            } else {
                // ❌ Login failed
                pw.println("<h3 style='color:red; text-align:center;'>Login Failed! Invalid email or password.</h3>");
                pw.println("<p style='text-align:center;'><a href='login.html'>Try Again</a></p>");
            }

            // 4️⃣ Close connection
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            pw.println("<h3 style='color:red; text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
