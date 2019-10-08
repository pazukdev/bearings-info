package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemView extends AbstractDto {

    private boolean searchEnabled;
    private boolean newItem;
    private boolean addToWishList;
    private RateReplacer rate;
    private String category;
    private TableDto header;
    private TableViewDto items;
    private PartsTable partsTable;
    private ReplacersTable replacersTable;
    private Long itemId;
    private List<NestedItemDto> possibleParts = new ArrayList<>();
    private List<NestedItemDto> replacers = new ArrayList<>();
    private Set<String> categories = new HashSet<>();
    private Set<Long> idsToRemove = new HashSet<>();
    private Long creatorId;
    private Set<Long> wishListIds = new HashSet<>();
    private Set<Long> ratedItems = new HashSet<>();

}
