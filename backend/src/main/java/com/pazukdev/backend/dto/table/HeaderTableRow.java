package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.util.CategoryUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderTableRow extends AbstractDto {

    private String parameter = "-";
    private String value = "-";
    private String itemId = "-";
    private String link;
    private boolean deletable;
    private int weight = 0;

    public static HeaderTableRow create(final String parameter, final String value) {
        final HeaderTableRow headerTableRow = new HeaderTableRow();
        headerTableRow.setName(parameter);
        headerTableRow.setParameter(parameter);
        headerTableRow.setValue(value);
        headerTableRow.setDeletable(!CategoryUtil.isFixed(parameter));
//        headerTableRow.setWeight(CategoryUtil.getWeight(parameter));
        return headerTableRow;
    }

}
