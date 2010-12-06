/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.vaadin;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import java.security.Principal;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = {"/ui/*", "/VAADIN/*"})
public class VaadinAppServlet extends AbstractApplicationServlet {

    @Inject Instance<VaadinApp> application;
    
    @Override
    protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
        return VaadinApp.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
		VaadinApp app = application.get();
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            throw new ServletException("Access denied");
        }

        // In this example, a user can be in one role only
        if (request.isUserInRole(UserRoles.ROLE_RESEARCHER)) {
            //app.setUserRole(UserRoles.ROLE_RESEARCHER);
        } else {
            throw new ServletException("Access denied");
        }

        app.setUser(principal);
        app.setLogoutURL(request.getContextPath() + "/logout.jsp");
        return app;
    }
}
