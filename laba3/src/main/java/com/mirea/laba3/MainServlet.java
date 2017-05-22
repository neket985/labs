package com.mirea.laba3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by nikitos on 20.05.17.
 */
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter pr = resp.getWriter();
        pr.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset = \"UTF-8\">\n" +
                "    <title> Список задач  </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<img src=\"pix.jpg\">\n" +
                "<form method = \"post\">\n" +
                "    задача: <input name=\"task\">\n" +
                "    <input type= \"submit\"  value= \"Добавить\">\n" +
                "</form>\n" +
                "<ol>\n" +
                "    <#list tasks as t>\n" +
                "        <li>\n" +
                "            <form method = \"post\" action = \"delete\">\n" +
                "                ${t.text}\n" +
                "                <input type=\"submit\" value=\"УДАЛИТЬ\">\n" +
                "                <input name = \"id\" type=\"hidden\" value=\"${t.id}\">\n" +
                "            </form>\n" +
                "        </li>\n" +
                "    </#list>\n" +
                "</ol>\n" +
                "</body>\n" +
                "</html>");
    }
}
