/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.vaadin;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import servicemonitor.util.DateTime;
import servicemonitor.dao.KassiEventsDao;
import servicemonitor.entity.ServiceEvents;

/**
 *
 * @author ktuomain
 */
public class KassiEventBrowser extends VerticalLayout implements Property.ValueChangeListener, Button.ClickListener {

    TextField userIdField = new TextField("User ID");
    PopupDateField fromDate = new PopupDateField("From date");
    PopupDateField toDate = new PopupDateField("To date");
    TextField actionField = new TextField("Action");
    TextField eventIdField = new TextField("Event ID");
    TextField maxEvents = new TextField("Maximum number of events");
    Table table = new Table();
    KassiEventsDao ced = new KassiEventsDao();
    //CosEventsDao ced = null;
    Button exportButton;

    public KassiEventBrowser() {
        fromDate.setResolution(InlineDateField.RESOLUTION_MIN);
        fromDate.setLocale(new Locale("fi"));
        fromDate.setImmediate(true);
        fromDate.setValue(new DateTime().addHours(-24)); // Default to current date
        fromDate.addListener(this);

        toDate.setResolution(InlineDateField.RESOLUTION_MIN);
        toDate.setLocale(new Locale("fi"));
        toDate.setImmediate(true);
        toDate.setValue(DateTime.now()); // Default to current date
        toDate.addListener(this);

        maxEvents.setValue("5000");
        maxEvents.setImmediate(true);

        actionField.addListener(this);
        eventIdField.addListener(this);
        maxEvents.addListener(this);

        Layout filters = new HorizontalLayout();
        filters.addComponent(userIdField);
        filters.addComponent(fromDate);
        filters.addComponent(toDate);
        filters.addComponent(actionField);
        filters.addComponent(eventIdField);
        filters.addComponent(maxEvents);

        exportButton = new Button("Export CSV", this);

        table.setSizeFull();
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setImmediate(true); // React at once when something is selected
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        BeanItemContainer<ServiceEvents> items = new BeanItemContainer<ServiceEvents>(ServiceEvents.class);
        table.setContainerDataSource(items);

        setSizeFull();
        addComponent(filters);
        addComponent(exportButton);
        addComponent(table);
        setExpandRatio(table, 1);
    }

    private String nullString(String value) {
        return (value != null && value.equals("")) ? null : value;
    }

    public void valueChange(ValueChangeEvent event) {
        search();
    }

    private void search() {
        String action = nullString((String) actionField.getValue());
        String semanticEventId = nullString((String) eventIdField.getValue());
        String userId = nullString((String) userIdField.getValue());

        Integer maxRowCount = 5000;
        try {
            maxRowCount = Integer.parseInt((String) maxEvents.getValue());
        } catch (NumberFormatException ex) {
            maxEvents.setValue("5000");
        }

        Collection events = ced.findEvents(
                (Date) fromDate.getValue(), (Date) toDate.getValue(),
                action, semanticEventId, userId, null, null, null, maxRowCount);
        if (events == null || events.isEmpty()) {
            events.add(new ServiceEvents());
            getWindow().showNotification(
                    "Warning",
                    "No entries found",
                    Notification.TYPE_WARNING_MESSAGE);
        }

        BeanItemContainer<ServiceEvents> items = new BeanItemContainer<ServiceEvents>(events);
        table.setContainerDataSource(items);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton() == exportButton) {
            getWindow().showNotification("Export button clicked");
            exportCSV();
        } else {
            getWindow().showNotification("Unknown button clicked");
        }
    }

    private void exportCSV() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

        PrintWriter out = new PrintWriter(output);
        char separator = (DecimalFormatSymbols.getInstance().getDecimalSeparator() == '.') ? ',' : ';';
        for (Object col : table.getContainerDataSource().getContainerPropertyIds()) {
            out.write("\"" + col.toString().replaceAll("\"", "") + "\"" + separator);
        }
        out.write("\n");
		for (Object row : table.getContainerDataSource().getItemIds()) {
			for (Object col : table.getContainerDataSource().getContainerPropertyIds()) {
				Object v = table.getContainerDataSource().getContainerProperty(row, col).getValue();
				if (v instanceof Date) {
					v = DateTime.DATE_TIME.format((Date) v);
				}
				String s = (v != null) ? v.toString() : "";
				// Excel only seems to support cells smaller than w255 x h500 / 32767 chars; only 1024 chars displayed in a cell (formula bar shows the rest)
				out.write("\"" + s.substring(0, Math.min(1024, s.length())).replaceAll("\"", "") + "\"" + separator);
			}
			out.write("\n");
		}

        final ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {

            public InputStream getStream() {
                return input;
            }
        };
        StreamResource streamResource = new StreamResource(streamSource, "events.csv", this.getApplication());
        streamResource.setCacheTime(5000); // no cache (<=0) does not work with IE8
        streamResource.setMIMEType("text/csv");
        getApplication().getMainWindow().open(streamResource, "_blank");
    }

}

