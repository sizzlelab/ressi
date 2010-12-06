/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.vaadin;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import servicemonitor.dao.RessiGroupDao;
import com.vaadin.data.Property.ValueChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import servicemonitor.entity.*;

/**
 *
 * @author kmtuomai
 */
public class UserGroupsView extends VerticalLayout implements Property.ValueChangeListener, Button.ClickListener {
    @Inject RessiGroupDao rgd;
    ComboBox groupSelect;
    Button saveButton;
    Button deleteButton;
    TwinColSelect userSelect;
    Collection<User> allUsers;

    @PostConstruct
    public void init() {
        //setSizeFull();

        groupSelect = new ComboBox("Please select a group");
        for (RessiGroup i : rgd.findAllGroups()) {
            groupSelect.addItem(i.getName());
        }

        groupSelect.setNewItemsAllowed(true);
        //groupSelect.setNewItemHandler(this);
        groupSelect.setImmediate(true);
        groupSelect.addListener(this);

        userSelect = new TwinColSelect("Please select some users");

        allUsers = rgd.findAllUsers();
        for (User i : allUsers) {
            userSelect.addItem(i.getUserId());
        }

        userSelect.setRows(25);
        userSelect.setNullSelectionAllowed(true);
        userSelect.setMultiSelect(true);
        userSelect.setImmediate(true);
        //userSelect.addListener(this);

        saveButton = new Button("Save", this);
        deleteButton = new Button("Delete group", this);

        addComponent(groupSelect);
        addComponent(userSelect);
        addComponent(saveButton);
        addComponent(deleteButton);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton() == saveButton) {
            getWindow().showNotification("Save button clicked");
            saveGroup();
        } else if (event.getButton() == deleteButton) {
            getWindow().showNotification("Delete group button clicked");
            deleteGroup();
        } else {
            getWindow().showNotification("Unknown button clicked");
        }
    }

    private void saveGroup() {
        String groupName = groupSelect.getValue().toString();
        if (groupName == null || groupName.equals("")) {
            getWindow().showNotification("Group must have a name!");
            return;
        }

        RessiGroup group = rgd.getOrCreateGroup(groupName);
        Collection<RessiGroupMember> selectedUsers = new ArrayList();
        for (User i : allUsers) {
            if (userSelect.isSelected(i.getUserId())) {
                RessiGroupMember ressiGroupMember = new RessiGroupMember();
                ressiGroupMember.setRessiGroup(group);
//                ressiGroupMember.setGroupId(group);
                ressiGroupMember.setUser(i);
//                ressiGroupMember.setUserId(i);
                selectedUsers.add(ressiGroupMember);
            }
        }
        group.setRessiGroupMemberCollection(selectedUsers);
        rgd.saveGroup(group);
    }

    private void deleteGroup() {
        String groupName = groupSelect.getValue().toString();
        if (groupName == null || groupName.equals("")) {
            getWindow().showNotification("Group must have a name!");
            return;
        }

        rgd.deleteGroup(groupName);
        groupSelect.removeItem(groupName);
    }

    public void valueChange(ValueChangeEvent event) {
        String groupName = groupSelect.getValue().toString();
        RessiGroup group = rgd.getOrCreateGroup(groupName);

        Set<String> groupMembers = new HashSet();
        if (group.getRessiGroupMemberCollection() != null) {
            for (RessiGroupMember i : group.getRessiGroupMemberCollection()) {
                groupMembers.add(i.getUser().getUserId());
            }
        }

        for (User i : allUsers) {
            if (groupMembers.contains(i.getUserId())) {
                userSelect.select(i.getUserId());
            } else {
                userSelect.unselect(i.getUserId());
            }
        }
    }
}

