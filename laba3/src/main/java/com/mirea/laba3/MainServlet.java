package com.mirea.laba3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by nikitos on 20.05.17.
 */
public class MainServlet extends HttpServlet {
    Statement stm;

    public MainServlet() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        stm = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laba", "postgres", "password")
                .createStatement();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        if (req.getServletPath().equals("/create")) {
            String task = req.getParameter("task");
            try {
                stm.execute("INSERT into list (todo) VALUES ('" + task + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (req.getServletPath().equals("/delete")) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            try {
                stm.execute("DELETE FROM list WHERE id = " + id + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter pr = resp.getWriter();
        String out = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset = \"UTF-8\">\n" +
                "    <title> Список задач  </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method = \"post\" action = \"create\">\n" +
                "    задача: <input name=\"task\">\n" +
                "    <input type= \"submit\"  value= \"Добавить\">\n" +
                "</form>\n" +
                "<ol>\n";
        try {
            ResultSet rs = stm.executeQuery("SELECT* FROM list");
            while (rs.next()) {
                out += "        <li>\n" +
                        "            <form method = \"post\" action = \"delete\">\n" +
                        rs.getString(2) +
                        "                <input type=\"submit\" value=\"УДАЛИТЬ\">\n" +
                        "                <input name = \"id\" type=\"hidden\" value=\"" +
                        rs.getInt(1) +
                        "\">\n" +
                        "            </form>\n" +
                        "        </li>\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        pr.print(out + "</ol>\n" +
                "</body>\n" +
                "</html>");
    }
}
