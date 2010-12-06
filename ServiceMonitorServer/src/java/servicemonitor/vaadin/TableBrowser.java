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
import com.vaadin.ui.ComboBox;
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
import servicemonitor.dao.*;
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
public class TableBrowser extends VerticalLayout implements Property.ValueChangeListener, Button.ClickListener {

    Table table = new Table();

    UserDao ud = new UserDao();
    MetalogDao md = new MetalogDao();
    ChannelDao cd = new ChannelDao();
    UserGroupDao ugd = new UserGroupDao();

    Button exportButton;
    ComboBox tableSelect;

    private void setItems(BeanItemContainer items) {
        table.setContainerDataSource(items);
    }

    private void loadUsers() {
        Collection users = ud.findAllUsers();
        if (users == null || users.isEmpty()) {
            users.add(new User());
            getWindow().showNotification(
                    "Warning",
                    "No entries found",
                    Notification.TYPE_WARNING_MESSAGE);
        }

        BeanItemContainer<User> items = new BeanItemContainer<User>(users);
        table.setContainerDataSource(items);
    }

    private void loadMetalogs() {
        Collection metalogs = md.findAllMetalogs();
        if (metalogs == null || metalogs.isEmpty()) {
            metalogs.add(new Metalog());
            getWindow().showNotification(
                    "Warning",
                    "No entries found",
                    Notification.TYPE_WARNING_MESSAGE);
        }

        BeanItemContainer<Metalog> items = new BeanItemContainer<Metalog>(metalogs);
        table.setContainerDataSource(items);
    }


    private void loadChannels() {
        Collection channels = cd.findAllChannels();
        if (channels == null || channels.isEmpty()) {
            channels.add(new Channel());
            getWindow().showNotification(
                    "Warning",
                    "No entries found",
                    Notification.TYPE_WARNING_MESSAGE);
        }

        BeanItemContainer<Channel> items = new BeanItemContainer<Channel>(channels);
        table.setContainerDataSource(items);
    }


    private void loadUserGroups() {
        Collection userGroups = ugd.findAllUserGroups();
        if (userGroups == null || userGroups.isEmpty()) {
            userGroups.add(new Channel());
            getWindow().showNotification(
                    "Warning",
                    "No entries found",
                    Notification.TYPE_WARNING_MESSAGE);
        }

        BeanItemContainer<UserGroup> items = new BeanItemContainer<UserGroup>(userGroups);
        table.setContainerDataSource(items);
    }


    public TableBrowser() {
        exportButton = new Button("Export CSV", this);
        tableSelect = new ComboBox("Please select a table");
        tableSelect.setImmediate(true);
        tableSelect.addListener((Property.ValueChangeListener) this);
        tableSelect.addItem("Metalog");
        tableSelect.addItem("Users");
        tableSelect.addItem("Channels");
        tableSelect.addItem("User groups");

        addComponent(tableSelect);

        table.setSizeFull();
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setImmediate(true); // React at once when something is selected
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);

//        loadMetalogs();

        setSizeFull();
        addComponent(exportButton);
        addComponent(table);
        setExpandRatio(table, 1);
    }

    private String nullString(String value) {
        return (value != null && value.equals("")) ? null : value;
    }

    public void valueChange(ValueChangeEvent event) {
        String tableName = (tableSelect.getValue() != null)
                ? tableSelect.getValue().toString() : "";
        if (tableName.equals("Users")) {
            loadUsers();
        } else if (tableName.equals("Metalog")) {
            loadMetalogs();
        } else if (tableName.equals("Channels")) {
            loadChannels();
        } else if (tableName.equals("User groups")) {
            loadUserGroups();
        }
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

