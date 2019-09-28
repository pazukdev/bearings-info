package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.converter.ReplacerConverter;
import com.pazukdev.backend.dto.item.NestedItemDto;
import com.pazukdev.backend.dto.item.NestedItemDtoFactory;
import com.pazukdev.backend.dto.item.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.item.TransitiveItemDto;
import com.pazukdev.backend.dto.table.*;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.repository.ReplacerRepository;
import com.pazukdev.backend.util.CategoryUtil;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.NestedItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.*;

import static com.pazukdev.backend.util.ItemUtil.createDescriptionMap;
import static com.pazukdev.backend.util.NestedItemUtil.prepareNestedItemDtosToConverting;
import static com.pazukdev.backend.util.SpecificStringUtil.replaceBlankWithDash;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<Item, TransitiveItemDto> {

    final TransitiveItemService transitiveItemService;
    @Getter
    private final ItemRepository itemRepository;
    private final ChildItemRepository partRepository;
    private final ReplacerRepository replacerRepository;
    private final ReplacerConverter replacerConverter;


    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final TransitiveItemService transitiveItemService,
                       final ChildItemRepository partRepository,
                       final ReplacerRepository replacerRepository,
                       final ReplacerConverter replacerConverter) {
        super(itemRepository, converter);
        this.transitiveItemService = transitiveItemService;
        this.itemRepository = itemRepository;
        this.partRepository = partRepository;
        this.replacerRepository = replacerRepository;
        this.replacerConverter = replacerConverter;
    }

    @Transactional
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Item findByName(String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    @Transactional
    public Item find(final String category, final String name) {
        for (final Item item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<Item> find(final String category) {
        return find(category, findAll());
    }

    @Transactional
    public List<Item> find(final String category, final List<Item> items) {
        final List<Item> categorizedItems = new ArrayList<>();
        for (final Item item : items) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

    @Transactional
    public Item saveAsItem(final TransitiveItem transitiveItem) {
        final Item item = getOrCreate(transitiveItem);
        item.setName(replaceBlankWithDash(item.getName()));
        return repository.save(item);
    }

    @Transactional
    public ItemView createItemView(final Long id) throws EntityExistsException {
        return createItemView(getOne(id));
    }

    @Transactional
    public ItemView update(final Long id, final ItemView itemView) {
        final Item item = getOne(id);
        final Map<String, String> headerMatrixMap = createHeaderMatrixMap(itemView);
        updateName(item, headerMatrixMap);
        updateDescription(item, headerMatrixMap);
        updateParts(item, itemView);
        updateReplacers(item, itemView);
        itemRepository.save(item);
        return createItemView(id);
    }

    private Map<String, String> createHeaderMatrixMap(final ItemView itemView) {
        final TableDto header = itemView.getHeader();
        final String[][] headerMatrix = header.getMatrix();
        final Map<String, String> headerMatrixMap = new HashMap<>();
        for (final String[] row : headerMatrix) {
            headerMatrixMap.put(row[0], row[1]);
        }
        return headerMatrixMap;
    }

    private void updateDescription(final Item item, final Map<String, String> headerMatrixMap) {
        final String newDescription = ItemUtil.toDescription(headerMatrixMap);
        applyNewDescriptionToCategory(item.getCategory(), headerMatrixMap);
        item.setDescription(newDescription);
    }

    public void applyNewDescriptionToCategory(final String category, final Map<String, String> newDescriptionMap) {
        final List<Item> allItemsOfCategory = find(category);
        for (final Item item : allItemsOfCategory) {
            final Map<String, String> oldItemDescriptionMap = ItemUtil.toMap(item.getDescription());
            final Map<String, String> newItemDescriptionMap = new HashMap<>(newDescriptionMap);
            for (final Map.Entry<String, String> entry : newItemDescriptionMap.entrySet()) {
                final String newParameter = entry.getKey();
                final String value = oldItemDescriptionMap.get(newParameter);
                if (value == null) {
                    entry.setValue("-");
                } else {
                    entry.setValue(value);
                }
            }
            item.setDescription(ItemUtil.toDescription(newItemDescriptionMap));
        }
    }

    private Set<Replacer> findEqualReplacer(final List<Replacer> replacers, final Replacer checkingReplacer) {
        final Long checkingReplacerId = checkingReplacer.getId();
        final Long checkingReplacerItemId = checkingReplacer.getItem().getId();
        final String checkingReplacerName = checkingReplacer.getName();
        final String checkingReplacerComment = checkingReplacer.getComment();

        final Set<Replacer> equalReplacers = new HashSet<>();
        for (final Replacer replacer : replacers) {
            final Long replacerId = replacer.getId();
            if (checkingReplacerId != null
                    && replacerId != null
                    && replacer.getId().equals(checkingReplacer.getId())) {
                equalReplacers.add(replacer);
                continue;
            }

            final Long replacerItemId = replacer.getItem().getId();
            final String replacerName = replacer.getName();
            final String replacerComment = replacer.getComment();
            if (replacerItemId.equals(checkingReplacerItemId)
                    && replacerName.equals(checkingReplacerName)
                    && replacerComment.equals(checkingReplacerComment)) {
                equalReplacers.add(replacer);
            }
        }
        return equalReplacers;
    }

    private void updateParts(final Item item, final ItemView itemView) {
        final Set<ChildItem> oldParts = new HashSet<>(item.getChildItems());
        final Set<ChildItem> newParts = new HashSet<>(createPartsFromItemView(item, itemView));
        item.getChildItems().clear();
        item.getChildItems().addAll(newParts);

        final List<ChildItem> toSave = new ArrayList<>();
        for (final ChildItem oldPart : oldParts) {
            for (final ChildItem newPart : newParts) {
                if (newPart.getName().equals(oldPart.getName())) {
                    toSave.add(oldPart);
                }
            }
        }
        oldParts.removeAll(toSave);
        for (final ChildItem orphan : oldParts) {
            partRepository.deleteById(orphan.getId());
        }
    }

    private void updateReplacers(final Item item, final ItemView itemView) {
        final Set<Replacer> oldReplacers = new HashSet<>(item.getReplacers());
        final Set<Replacer> newReplacers = new HashSet<>(createReplacersFromItemView(item, itemView));
        item.getReplacers().clear();
        item.getReplacers().addAll(newReplacers);

        final List<Replacer> toSave = new ArrayList<>();
        for (final Replacer oldReplacer : oldReplacers) {
            for (final Replacer newReplacer : newReplacers) {
                if (newReplacer.getName().equals(oldReplacer.getName())) {
                    toSave.add(oldReplacer);
                }
            }
        }
        oldReplacers.removeAll(toSave);
        for (final Replacer orphan : oldReplacers) {
            replacerRepository.deleteById(orphan.getId());
        }
    }

    private Set<ChildItem> createPartsFromItemView(final Item item, final ItemView itemView) {
        final List<NestedItemDto> allItems = NestedItemUtil.collectAllItems(itemView.getPartsTable());
        final List<NestedItemDto> preparedItems = prepareNestedItemDtosToConverting(item, allItems);

        final Set<ChildItem> partsFromItemView = new HashSet<>();
        for (final NestedItemDto nestedItem : preparedItems) {
            final Item partItem = getOne(nestedItem.getItemId());

            final ChildItem part = new ChildItem();
            part.setId(nestedItem.getId());
            part.setName(nestedItem.getName());
            part.setItem(partItem);
            part.setLocation(nestedItem.getLocation());
            part.setQuantity(nestedItem.getQuantity());

            partsFromItemView.add(part);
        }

        return partsFromItemView;
    }

    private Set<Replacer> createReplacersFromItemView(final Item item, final ItemView itemView) {
        final ReplacersTable replacersTable = itemView.getReplacersTable();
        final List<NestedItemDto> dtos = prepareNestedItemDtosToConverting(item, replacersTable.getReplacers());

        final Set<Replacer> replacersFromItemView = new HashSet<>();
        for (final NestedItemDto dto : dtos) {
            final Item replacerItem = getOne(dto.getItemId());

            final Replacer replacer = new Replacer();
            replacer.setId(dto.getId());
            replacer.setName(dto.getName());
            replacer.setItem(replacerItem);
            replacer.setComment(dto.getComment());

            replacersFromItemView.add(replacer);
        }

        return replacersFromItemView;
    }

    private void updateName(final Item item, final Map<String, String> headerMatrixMap) {
        final String oldName = item.getName();
        final String newName = headerMatrixMap.get("Name");
        if (newName != null && !newName.equals(oldName)) {
            item.setName(newName);
            applyToAllItemDescriptions(item.getCategory(), oldName, newName);
        }
        headerMatrixMap.remove("Name");
    }

    private void applyToAllItemDescriptions(final String updatingItemCategory,
                                            final String oldValue,
                                            final String newValue) {
        final List<Item> items = findAll();
        final Set<String> categories = findCategories(items);
        for (final Item item : items) {
            final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
            for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
                final String value = entry.getValue().split(" \\(")[0];
                if (value.equals(oldValue)) {
                    if (categories.contains(entry.getKey())) {
                        if (entry.getKey().equals(updatingItemCategory)) {
                            entry.setValue(entry.getValue().replace(oldValue, newValue));
                        }
                    } else {
                        entry.setValue(entry.getValue().replace(oldValue, newValue));
                    }
                }
            }
            final String newDescription = ItemUtil.toDescription(descriptionMap);
            item.setDescription(newDescription);
            itemRepository.save(item);
        }
    }

    public ItemView createItemView(final Item item) {
        final List<Item> allItems = findAll();
        final List<Item> sameCategoryItems = find(item.getCategory(), allItems);

        final ItemView itemView = new ItemView();
        itemView.setCategory(item.getCategory());
        itemView.setItemId(item.getId());
        itemView.setHeader(createHeader(item));
        itemView.setItems(createTableView(new ArrayList<>(item.getChildItems())));
        itemView.setPartsTable(createPartsTable(item));
        itemView.setReplacersTable(createReplacersTable(item));
//        itemView.setAllItems(createItemSelects(allItems));
//        itemView.setSameCategoryItems(createItemSelects(sameCategoryItems));
        itemView.getPossibleParts().addAll(createPossibleParts(allItems));
        itemView.getReplacers().addAll(createReplacerDtos(sameCategoryItems));
        return itemView;
    }

    private List<NestedItemDto> createPossibleParts(final List<Item> items) {
        final List<NestedItemDto> childItemDtos = new ArrayList<>();
        for (final Item item : items) {
            final String category = item.getCategory();
            if (!CategoryUtil.isPartCategory(category)) {
                continue;
            }
            childItemDtos.add(NestedItemDtoFactory.createBasicNestedItemDto(item));
        }
        return childItemDtos;
    }

    private List<NestedItemDto> createReplacerDtos(final List<Item> items) {
        final List<NestedItemDto> replacerDtos = new ArrayList<>();
        for (final Item item : items) {
            replacerDtos.add(NestedItemDtoFactory.createBasicNestedItemDto(item));
        }
        return replacerDtos;
    }

//    private List<ItemSelect> createItemSelects(final List<Item> items) {
//        final List<ItemSelect> itemSelects = new ArrayList<>();
//        for (final Item item : items) {
//            String manufacturer = ItemUtil.getValueFromDescription(item.getDescription(), "Manufacturer");
//            String name = item.getName();
//            if (manufacturer != null) {
//                name = manufacturer + " " + item.getName();
//            }
//            if (item.getCategory().equals("Seal")) {
//                final String size = ItemUtil.getValueFromDescription(item.getDescription(), "Size");
//                name = size + " " + manufacturer + " " + item.getName();
//            }
//            final ItemSelect itemSelect = new ItemSelect();
//            itemSelect.setName(name);
//            itemSelect.setItemName(item.getName());
//            itemSelect.setItemId(item.getId());
//            itemSelects.add(itemSelect);
//        }
//        return itemSelects;
//    }

    private TableDto createHeader(final Item item) {
        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final String tableName = item.getCategory().replace(" (i)", "") + " " + item.getName();
        return createTable(tableName, ItemUtil.toMap(item.getDescription()), list);
    }

    private TableDto createTable(final String tableName,
                                 final Map<String, String> descriptionMap,
                                 final List<String[]> list) {
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            final String parameter = entry.getKey();
            final String value = entry.getValue();
            String itemId = "no id";
            String message = "";
            final Item foundItem = find(parameter, value);
            if (foundItem != null) {
                itemId = foundItem.getId().toString();
                message = "show button";
            }
            list.add(new String[]{parameter, value, itemId, message});
        }
        return new TableDto(tableName, listToMatrix(list));
    }

    private PartsTable createPartsTable(final Item item) {
        if (!CategoryUtil.itemIsAbleToContainParts(item)) {
            return stubPartsTable();
        }
        final PartsTable partsTable = new PartsTable();
        partsTable.setName("Parts");
        final List<ChildItem> parts = new ArrayList<>(item.getChildItems());
        for (final ChildItem part : parts) {
            final Item partItem = part.getItem();
            final String buttonText = ItemUtil.createButtonText(partItem);

            final NestedItemDto partDto = new NestedItemDto();
            partDto.setId(part.getId());
            partDto.setName(part.getName());
            partDto.setItemId(partItem.getId());
            partDto.setItemName(partItem.getName());
            partDto.setItemCategory(partItem.getCategory());
            partDto.setQuantity(part.getQuantity());
            partDto.setButtonText(buttonText);
            partDto.setLocation(part.getLocation());

            partsTable.getParts().add(partDto);
        }
        return PartsTable.create(partsTable.getParts(), findAllPartCategories());
    }

    private ReplacersTable createReplacersTable(final Item item) {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("Replacers");
        final List<Replacer> replacers = new ArrayList<>(item.getReplacers());
        for (final Replacer replacer : replacers) {
            final String buttonText = ItemUtil.createButtonText(replacer.getItem());

            final NestedItemDto replacerDto = replacerConverter.convertToDto(replacer, buttonText);
            replacerDto.setItemCategory(replacer.getItem().getCategory());

            replacersTable.getReplacers().add(replacerDto);
        }
        return replacersTable;
    }

    public ItemView motorcycleCatalogue() {
        final List<Item> motorcycles = find("Motorcycle");

        final String tableName = "Motorcycle catalogue";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Models", String.valueOf(motorcycles.size())});

        final ItemView itemView = new ItemView();
        itemView.setSearchEnabled(false);
        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        final int noMatterWhatNumber = 123;
        final List<TableDto> tables = new ArrayList<>(Collections.singletonList(motorcyclesTable(motorcycles)));
        itemView.setItems(new TableViewDto(noMatterWhatNumber, tables));
        itemView.setPartsTable(stubPartsTable());
        itemView.setReplacersTable(stubReplacersTable());
        return itemView;
    }

    private TableDto stubTable() {
        return new TableDto("stub", new String[][]{{""}});
    }

    private PartsTable stubPartsTable() {
        final PartsTable partsTable = new PartsTable();
        partsTable.setName("stub");
        return partsTable;
    }

    private ReplacersTable stubReplacersTable() {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("stub");
        return replacersTable;
    }

    private String[][] listToMatrix(List<String[]> list) {
        int j = 0;
        String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        return matrix;
    }

    public TableDto motorcyclesTable(final List<Item> motorcycles) {
        final String tableName = "Motorcycles";
        final List<String[]> rows = new ArrayList<>();
        for (final Item motorcycle : motorcycles) {
            final String[] row = {
                    ItemUtil.getValueFromDescription(motorcycle.getDescription(), "Production"),
                    motorcycle.getName(),
                    ItemUtil.getValueFromDescription(motorcycle.getDescription(), "Manufacturer"),
                    motorcycle.getId().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    private Item createSelectableStub(final String value, final String category) {
        final Item selectableStub = new Item();
        selectableStub.setCategory(category);
        selectableStub.setName(value);
        return selectableStub;
    }

    public Item getUssrSealBySize(final String searchingSize) {
        final List<Item> ussrSeals = filter(find("Seal"), "Manufacturer", "USSR");
        for (Item seal : ussrSeals) {
            final String actualSize = ItemUtil.getValueFromDescription(seal.getDescription(), "Size, mm");
            if (actualSize.equals(searchingSize)) {
                return seal;
            }
        }
        return null;
    }

    private List<Item> filter(final List<Item> items,
                              final String parameter,
                              final String searchingValue) {
        final List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            final String value = ItemUtil.getValueFromDescription(item.getDescription(), parameter);
            if (value != null && value.equals(searchingValue)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public TableViewDto createTableView(final List<ChildItem> childItems) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<ChildItem> categorizedChildItems : ItemUtil.categorizeChildItems(childItems)) {
            tables.add(createTable(categorizedChildItems));
        }
        return new TableViewDto(childItems.size(), tables);
    }

    public TableDto createTable(final List<ChildItem> childItems) {
        final String tableName = childItems.get(0).getItem().getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ChildItem childItem : childItems) {
            final Item item = childItem.getItem();
            final String[] row = {
                    childItem.getLocation(),
                    item.getCategory().equals("Seal")
                            ? ItemUtil.getValueFromDescription(item.getDescription(), "Size, mm")
                            : item.getName(),
                    childItem.getQuantity() != null ? childItem.getQuantity() : "0",
                    item.getId().toString()
            };

            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public Item getOrCreate(final TransitiveItem transitiveItem) {
        final Item item = find(transitiveItem.getCategory(), transitiveItem.getName());
        return item != null ? item : create(transitiveItem);
    }

    public Item create(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        final Item item = new Item();
        item.setName(transitiveItem.getName());
        item.setCategory(transitiveItem.getCategory().replace(" (i)", ""));
        item.setDescription(createItemDescription(transitiveItem));
        item.getChildItems().addAll(createParts(transitiveItem, descriptionMap.getItems()));
        item.getReplacers().addAll(createReplacers(transitiveItem));
        return item;
    }

    public String createItemDescription(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        descriptionMap.getItems().clear();
        return ItemUtil.toDescription(descriptionMap);
    }

    public List<ChildItem> createParts(final TransitiveItem parent,
                                       final Map<String, String> childItemsDescription) {
        final List<ChildItem> childItems = new ArrayList<>();
        for (final Map.Entry entry : childItemsDescription.entrySet()) {
            final String category = entry.getKey().toString();
            if (entry.getValue().toString().contains(";")) {
                final String[] names = entry.getValue().toString().split("; ");
                for (final String name : names) {
                    final ChildItem childItem = createChildItem(parent, name, category);
                    if (childItem != null) {
                        childItems.add(childItem);
                    }
                }
            } else {
                final String name = entry.getValue().toString();
                final ChildItem childItem = createChildItem(parent, name, category);
                if (childItem != null) {
                    childItems.add(childItem);
                }
            }
        }

        return childItems;
    }

    public ChildItem createChildItem(final TransitiveItem parent, final String value, final String category) {
        String name;
        String location = "";
        String quantity;
        if (SpecificStringUtil.containsParentheses(value)) {
            name = SpecificStringUtil.getStringBeforeParentheses(value);
            String additionalData = SpecificStringUtil.getStringBetweenParentheses(value);
            location = additionalData.contains(" - ") ? additionalData.split(" - ")[0] : "-";
            quantity = additionalData.contains(" - ") ? additionalData.split(" - ")[1] : additionalData;
        } else {
            name = value;
            location = "-";
            quantity = category.equals("Spark plug") ? "2" : "1";
        }
        final TransitiveItem oldChild = category.equals("Seal")
                ? transitiveItemService.getUssrSealBySize(name)
                : transitiveItemService.find(category, name);

        if (oldChild != null) {
            Item child = getOrCreate(oldChild);

            final ChildItem childItem = new ChildItem();
            childItem.setName(parent.getName() + " - " + name);
            childItem.setItem(child);
            childItem.setLocation(location);
            childItem.setQuantity(quantity);
            return childItem;
        } else {
            return null;
        }
    }

    private List<Replacer> createReplacers(final TransitiveItem transitiveItem) {
        final List<Replacer> replacers = new ArrayList<>();
        final String replacersSourceString = transitiveItem.getReplacer();
        if (replacersSourceString == null || replacersSourceString.equals("-")) {
            return replacers;
        }
        for (final String replacerData : Arrays.asList(replacersSourceString.split("; "))) {
            String replacerName;
            String comment = null;
            if (SpecificStringUtil.containsParentheses(replacerData)) {
                replacerName = SpecificStringUtil.getStringBeforeParentheses(replacerData);
                comment = SpecificStringUtil.getStringBetweenParentheses(replacerData);
            } else {
                replacerName = replacerData;
            }
            final String category = transitiveItem.getCategory();
            final TransitiveItem transitiveReplacerItem = transitiveItemService.find(category, replacerName);
            final Item replacerItem = getOrCreate(transitiveReplacerItem);

            final Replacer replacer = new Replacer();
            replacer.setName(NestedItemUtil.createName(transitiveItem.getName(), replacerName));
            replacer.setItem(replacerItem);
            if (comment != null) {
                replacer.setComment(comment);
            }

            replacers.add(replacer);
        }

        return replacers;
    }

    public Set<String> findCategories(final List<Item> items) {
        final Set<String> categories = new HashSet<>();
        for (final Item item : items) {
            categories.add(item.getCategory());
        }
        return categories;
    }

    public Set<String> findAllCategories() {
        return findCategories(findAll());
    }

    public Set<String> findAllPartCategories() {
        return CategoryUtil.filterPartCategories(findAllCategories());
    }

}
