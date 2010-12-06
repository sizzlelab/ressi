/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.vaadin;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author kmtuomai
 */
public class DashboardView extends VerticalLayout {

    public DashboardView() {
		setSizeFull();
        Embedded e = new Embedded("", new ExternalResource("/ServiceMonitorServer/dashboard.jsp"));
        e.setType(Embedded.TYPE_BROWSER);
		e.setSizeFull();
        addComponent(e);
    }

}