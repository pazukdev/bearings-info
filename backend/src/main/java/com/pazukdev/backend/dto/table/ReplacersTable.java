package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.item.ReplacerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReplacersTable extends AbstractDto {

    private List<ReplacerDto> replacers = new ArrayList<>();

}
