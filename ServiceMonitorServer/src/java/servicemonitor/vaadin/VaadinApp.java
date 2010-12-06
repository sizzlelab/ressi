/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.vaadin;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import servicemonitor.dao.RessiGroupDao;

@SessionScoped
public class VaadinApp extends Application implements SelectedTabChangeListener {

    @Inject UserGroupsView userGroupsView;
    @Inject MotionChartView motionChartView;
    @Inject RessiGroupDao rgd;

    @Override
    public void init() {
        if (rgd != null)
        System.out.println("rgd is not null");
        else
        System.out.println("rgd is null");

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addTab(new DashboardView(), "Dashboard", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
		tabs.addTab(userGroupsView, "User Groups", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
		tabs.addTab(motionChartView, "User Group Motion Chart", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
    	tabs.addTab(new EventBrowser(), "Event Browser", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
        tabs.addTab(new KassiEventBrowser(), "Kassi Event Browser", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
        tabs.addTab(new TableBrowser(), "Data Browser", new ExternalResource("http://demo.vaadin.com/VAADIN/themes/runo/icons/16/users.png"));
		tabs.addListener(this);
		
        VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(tabs);

        Window mainWindow = new Window("Ressi Web UI", layout);
		setTheme("ressi");
        setMainWindow(mainWindow);
    }

    @Override
    public void close() {
        super.close();
    }

	public void selectedTabChange(SelectedTabChangeEvent event) {
		final TabSheet source = (TabSheet) event.getSource();
        if (source instanceof TabSheet) {
            if (source.getSelectedTab() instanceof SelectedTabChangeListener) {
				((SelectedTabChangeListener)source.getSelectedTab()).selectedTabChange(event);
			}
		}
	}
}
