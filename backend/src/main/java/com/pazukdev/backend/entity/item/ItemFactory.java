package com.pazukdev.backend.entity.item;

import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Component
public class ItemFactory extends AbstractEntityFactory<ItemEntity> {

    private final ItemService service;

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.filePath("item");
    }

    @Override
    public ItemEntity createEntity() {
        return new ItemEntity();
    }

    @Override
    protected void applyCharacteristics(ItemEntity item, TableRow tableRow) {
        super.applyCharacteristics(item, tableRow);

        applyCategory(item, tableRow);
        applyQuantity(item, tableRow);
        applyDescription(item, tableRow);
        applyReplacer(item, tableRow);
    }

    private void applyCategory(final ItemEntity item, final TableRow tableRow) {
        final String category = tableRow.getData().get("category");
        item.setCategory(category);
    }

    private void applyQuantity(final ItemEntity item, final TableRow tableRow) {
        final String quantity = tableRow.getData().get("quantity");
        if (quantity != null) {
            item.setQuantity(Integer.valueOf(quantity));
        }
    }

    private void applyDescription(final ItemEntity item, final TableRow tableRow) {
        String description = "";
        for (final Map.Entry<String, String> entry : tableRow.getData().entrySet()) {
            final String key = entry.getKey();
            if (key.equals("name") || key.equals("category") || key.equals("quantity") || key.equals("replacer")) {
                continue;
            }
            description = description + entry.getKey() + ":" + entry.getValue() + ";;";
        }
        item.setDescription(description);
    }

    private void applyReplacer(final ItemEntity item, final TableRow tableRow) {
        final String replacer = tableRow.getData().get("replacer");
        item.setReplacer(replacer != null ? replacer : "-");
    }

}
