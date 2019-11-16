package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
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
    private String message = "-";

    public static HeaderTableRow create(final String parameter, final String value) {
        final HeaderTableRow headerTableRow = new HeaderTableRow();
        headerTableRow.setName(parameter);
        headerTableRow.setParameter(parameter);
        headerTableRow.setValue(value);
        return headerTableRow;
    }

}
