package tk.gbl.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Date: 2015/1/7
 * Time: 10:29
 *
 * @author Tian.Dong
 */
public class TestServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String content = req.getParameter("content");
    System.out.println(content);
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
  }
}
