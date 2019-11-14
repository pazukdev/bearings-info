package com.pazukdev.backend.dto;

import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.InputStream;
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
    private boolean hardDelete = false;
    private NestedItemDto userData;
    private String imgData = "-";
    private InputStream file;
    private RateReplacer rate;
    private String category;
    private TableDto header;
    private PartsTable partsTable;
    private ReplacersTable replacersTable;
    private Long itemId;
    private List<NestedItemDto> possibleParts = new ArrayList<>();
    private List<NestedItemDto> replacers = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private Set<Long> idsToRemove = new HashSet<>();
    private Long creatorId;
    private String creatorName;
    private Set<Long> wishListIds = new HashSet<>();
    private Set<Long> ratedItems = new HashSet<>();

}
