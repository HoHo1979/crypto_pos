package com.iotarch.cryptopos;

import com.iotarch.cryptopos.entity.Category;
import com.iotarch.cryptopos.entity.Item;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Viewport;
import com.vaadin.board.Board;
import com.vaadin.board.Row;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringUI
@Viewport("width=device-width")
public class MyUI extends UI {

    MockData mockData = new MockData();
    private Grid<Item> itemGrid;
    List<Item> gridItemList = new ArrayList<>();
    ListDataProvider<Item> itemListDataProvider= new ListDataProvider<Item>(gridItemList);
    private TextField numberField;
    private Label selectItem;
    private TabSheet itemTabSheet;
    private List<String> tabNames;
    private Button comfirmedButton;
    private Item matchedItem;


    @Override
    protected void init(VaadinRequest vaadinRequest) {


        Board mainBoard = new Board();

        HorizontalLayout hlayout = new HorizontalLayout();

        itemGrid = createGrid();

        itemGrid.setDataProvider(itemListDataProvider);

        tabNames = new ArrayList<>();

        itemTabSheet = createTabSheet();

        selectItem = new Label();
        selectItem.setCaption("Selected Item");

        numberField = new TextField("Please Enter the Quantity");
        numberField.setVisible(false);
        numberField.setValue("1");

        comfirmedButton = new Button("Enter");

        comfirmedButton.addClickListener(this::itemSelected);

        VerticalLayout rightLayout = new VerticalLayout(itemTabSheet,new HorizontalLayout(selectItem, numberField, comfirmedButton));

        hlayout.addComponents(itemGrid,rightLayout);

        VerticalLayout vlayout = new VerticalLayout();

        vlayout.addComponent(hlayout);

        hlayout.setMargin(true);

        MButton cashButton = new MButton("Cash")
                                .withIcon(VaadinIcons.CASH);

        MButton creditButton = new MButton("Credit Card")
                                .withIcon(VaadinIcons.CREDIT_CARD);

        MButton XRPButton = new MButton("XRP")
                                .withIcon(VaadinIcons.ROCKET);

        MButton LTCButton = new MButton("LTC")
                                .withIcon(VaadinIcons.FIRE);

        MHorizontalLayout paymentLayout = new MHorizontalLayout()
                .withCaption("Payment Method")
                .withComponents(cashButton,creditButton,XRPButton,LTCButton);


        Row row = mainBoard.addRow(
                hlayout
        );

        Row paymentRow=mainBoard.addRow(paymentLayout);

        setContent(mainBoard);


    }

    private void itemSelected(Button.ClickEvent clickEvent) {

        Item item = new Item();

        String number = numberField.getValue();

        if(Double.valueOf(number)>0 && matchedItem!=null){

            item=copyItem(matchedItem,item);

            item.setItemQuantity(Double.valueOf(number));

        }else{
            Notification.show("Please Enter Positive number");
        }

        if(matchedItem!=null){

            gridItemList.add(item);

            itemListDataProvider.refreshAll();

        }


    }

    private Item copyItem(Item matchedItem, Item item) {

            item.setItemName(matchedItem.getItemName());
            item.setItemPrice(matchedItem.getItemPrice());

        return item;
    }

    private Grid<Item> createGrid() {

        Grid<Item> itemGrid = new Grid();

        itemGrid.addColumn(Item::getItemName).setCaption("Name");
        itemGrid.addColumn(Item::getItemPrice).setCaption("Price");
        itemGrid.addColumn(Item::getItemQuantity).setCaption("Quantity");
        itemGrid.addColumn(i-> i.getItemPrice()*i.getItemQuantity()).setCaption("Total");


        return itemGrid;

    }

    private TabSheet createTabSheet() {

        TabSheet tabSheet = new TabSheet();


        for(Category category: mockData.getCategoryList()) {
                tabSheet.addTab(new GridLayout(5,5),category.getCategoryName());
        }


        tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {

                TabSheet tabSheet1 = selectedTabChangeEvent.getTabSheet();

                Layout tabLayout = (Layout) tabSheet1.getSelectedTab();

                String tabName=tabSheet1.getTab(tabLayout).getCaption();

                tabLayout.removeAllComponents();


                Optional<Category> categoryOptional = mockData.getCategoryList().stream().
                        filter(x->x.getCategoryName().equals(tabName)).findAny();

                if(categoryOptional.isPresent()){

                        List<Item> itemList = categoryOptional.get().getItemList();

                        for(Item item: itemList){

                            Button button = new Button(item.getItemName());
                            button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
                            button.addClickListener(c->buttonClicked(c.getButton().getCaption()));
                            tabLayout.addComponent(button);

                        }

                }

                }

        });

        return tabSheet;
    }

    private void buttonClicked(String caption) {

        numberField.setValue("1");

        for(Category category:mockData.getCategoryList()) {

            Optional<Item> optionalItem = category.getItemList()
                                                  .stream()
                                                  .filter(i -> i.getItemName().equals(caption))
                                                  .findFirst();

            if (optionalItem.isPresent()) {
                matchedItem = optionalItem.get();
                selectItem.setValue(matchedItem.getItemName());
                numberField.setVisible(true);
                break;
            }

        }


    }
}
