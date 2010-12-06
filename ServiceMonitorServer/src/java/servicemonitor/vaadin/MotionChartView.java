/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.vaadin;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import servicemonitor.util.DateTime;
import servicemonitor.dao.RessiGroupDao;
import servicemonitor.entity.RessiGroup;

/**
 *
 * @author kmtuomai
 */
public class MotionChartView extends VerticalLayout implements ValueChangeListener, SelectedTabChangeListener {

    @Inject RessiGroupDao rgd;
    ComboBox groupSelect;
    PopupDateField fromDate = new PopupDateField("From date");
    PopupDateField toDate = new PopupDateField("To date");
    Embedded e;

    @PostConstruct
    public void init() {
        groupSelect = new ComboBox("Please select a group");
        groupSelect.setImmediate(true);
        groupSelect.addListener((Property.ValueChangeListener) this);
        loadGroups();

        fromDate.setResolution(InlineDateField.RESOLUTION_DAY);
        fromDate.setLocale(new Locale("fi"));
        fromDate.setImmediate(true);
        fromDate.setValue(new DateTime().addHours(-24)); // Default to yesterday
        fromDate.addListener((Property.ValueChangeListener) this);

        toDate.setResolution(InlineDateField.RESOLUTION_DAY);
        toDate.setLocale(new Locale("fi"));
        toDate.setImmediate(true);
        toDate.setValue(DateTime.now()); // Default to current date
        toDate.addListener((Property.ValueChangeListener) this);

        e = new Embedded("Motion Chart", new ExternalResource("/ServiceMonitorServer/secure/empty.jsp"));
        e.setType(Embedded.TYPE_BROWSER);
        //e.setWidth("100%");
        //e.setHeight("500px");
		e.setSizeFull();

		HorizontalLayout filters = new HorizontalLayout();
		filters.addComponent(groupSelect);
		filters.addComponent(fromDate);
		filters.addComponent(toDate);

		setSizeFull();
		addComponent(filters);
		addComponent(e);
		setExpandRatio(e, 1);
    }

    public void valueChange(ValueChangeEvent event) {
        String groupName = (groupSelect.getValue() != null)
                ? groupSelect.getValue().toString() : "";

        getWindow().showNotification("Selected group: " + groupName);
        load(groupName);
    }

    public void selectedTabChange(SelectedTabChangeEvent event) {
        loadGroups();
    }

    private void load(String groupName) {
        Date fromD = (Date) fromDate.getValue();
        Date toD = (Date) toDate.getValue();
        long start = fromD.getTime();
        long end = toD.getTime();
        try {
            if (groupName != null && !groupName.equals("")) {
            //    e.setSource(new ExternalResource("/ServiceMonitorServer/secure/motionChart.jsp?groupName=" + URLEncoder.encode(groupName, "UTF-8") + "&start=" + start + "&end=" + end));
                e.setSource(new ExternalResource("/ServiceMonitorServer/MotionChartServlet?groupName=" + URLEncoder.encode(groupName, "UTF-8") + "&start=" + start + "&end=" + end));

//                MotionChartServlet
            } else {
                e.setSource(new ExternalResource("/ServiceMonitorServer/secure/empty.jsp"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadGroups() {
        groupSelect.removeAllItems();

        for (RessiGroup i : rgd.findAllGroups()) {
            groupSelect.addItem(i.getName());
        }
    }

}

