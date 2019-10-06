package com.pazukdev.backend.util;

import com.pazukdev.backend.converter.ReplacerConverter;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pazukdev.backend.util.TableUtil.*;

public class ItemViewUtil {

    public static ItemView createPartsView(final ItemView itemView,
                                           final Integer size,
                                           final String tableName,
                                           final String parameter,
                                           final PartsTable table,
                                           final ItemService itemService) {

        List<String[]> list = new ArrayList<>();
        list.add(new String[]{parameter, String.valueOf(size)});

        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        final int noMatterWhatNumber = 123;
        final List<TableDto> tables = new ArrayList<>(Collections.singletonList(stubTable()));
        itemView.setItems(new TableViewDto(noMatterWhatNumber, tables));
        itemView.setPartsTable(table);
        itemView.setReplacersTable(stubReplacersTable());
        itemView.setCategories(itemService.findAllCategories());
        return itemView;
    }

    public static ItemView createOrdinaryItemView(final ItemView itemView,
                                                  final Long itemId,
                                                  final ItemService itemService,
                                                  final ReplacerConverter replacerConverter,
                                                  final UserService userService) {
        final Item item = itemService.getOne(itemId);
        final List<Item> allItems = itemService.findAll();
        final List<Item> sameCategoryItems = itemService.find(item.getCategory(), allItems);

        itemView.setSearchEnabled(true);
        itemView.setCategory(item.getCategory());
        itemView.setHeader(createHeader(item, itemService));
        itemView.setItems(createTableView(new ArrayList<>(item.getChildItems()), itemService));
        itemView.setPartsTable(createPartsTable(item, "Parts", itemService, userService));
        itemView.setReplacersTable(createReplacersTable(item, replacerConverter));
        itemView.getPossibleParts().addAll(NestedItemUtil.createPossibleParts(allItems, userService));
        itemView.getReplacers().addAll(NestedItemUtil.createReplacerDtos(sameCategoryItems, userService));
        itemView.setCreatorId(item.getCreatorId());
        return itemView;
    }

    public static ItemView createWishListView(final ItemView itemView,
                                              final WishList wishList,
                                              final ItemService itemService,
                                              final UserService userService) {
        final List<Item> items = new ArrayList<>(wishList.getItems());

        final String tableName = "Your wish list";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Items", String.valueOf(items.size())});

        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        final int noMatterWhatNumber = 123;
        final List<TableDto> tables = new ArrayList<>(Collections.singletonList(stubTable()));
        itemView.setItems(new TableViewDto(noMatterWhatNumber, tables));
        itemView.setPartsTable(specialItemsTable(items, itemService, userService));
        itemView.setReplacersTable(stubReplacersTable());
        itemView.setCategories(itemService.findAllCategories());
        return itemView;
    }

    public static ItemView createItemsManagementView(final ItemView itemView,
                                                     final ItemService itemService,
                                                     final UserService userService) {
        final List<Item> allItems = itemService.findAll();

        final String tableName = "Items management";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Items", String.valueOf(allItems.size())});

        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        final int noMatterWhatNumber = 123;
        final List<TableDto> tables = new ArrayList<>(Collections.singletonList(stubTable()));
        itemView.setItems(new TableViewDto(noMatterWhatNumber, tables));
        itemView.setPartsTable(specialItemsTable(itemService.findAll(), itemService, userService));
        itemView.setReplacersTable(stubReplacersTable());
        itemView.setCategories(itemService.findAllCategories());
        return itemView;
    }

}
