package org.vaadin.example;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.vaadin.example.model.DataModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;
import org.vaadin.example.services.EntityService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */

@Route("")
public class MainView extends VerticalLayout {

    private final Grid<DataModel> userGrid = new Grid<>();
    private ListDataProvider<DataModel> dataProvider;

    @Autowired
    public MainView(EntityService service) {

        dataProvider = DataProvider.ofCollection((new ArrayList<>(service.findAll())));

        VerticalLayout content = new VerticalLayout();

        userGrid.addColumn(DataModel::getName).setHeader("Name").setSortable(true);
        userGrid.addColumn(DataModel::getEmail).setHeader("Mail").setSortable(true);
        userGrid.addColumn(DataModel::getId).setHeader("Id").setSortable(true);
        userGrid.addComponentColumn(item -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                service.delete(item);
            });
            return null;
        });
        userGrid.setItems(dataProvider);
        content.add(userGrid);
        removeAll();
        add(content);
    }
}
