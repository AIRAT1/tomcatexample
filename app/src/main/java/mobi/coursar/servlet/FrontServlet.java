package mobi.coursar.servlet;

import de.Helper;
import mobi.coursar.exception.DemoException;
import mobi.coursar.repository.DemoRepository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

public class FrontServlet extends HttpServlet {
    private DemoRepository repository;

    @Override
    public void init() throws ServletException {
        System.out.println("INIT SERVLET");
        try {
            final InitialContext context = new InitialContext();
            final DataSource dataSource = (DataSource)context.lookup("java:/comp/env/jdbc/db");
            final String uploadPath = System.getenv("UPLOAD_PATH");
            repository = new DemoRepository(dataSource);
        }catch (NamingException e) {
            throw new DemoException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.getWriter().write(Helper.help());
        resp.getWriter().write(repository.getAll().toString());
    }
}
