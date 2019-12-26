package com.pazukdev.backend.dto;

import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
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
    private boolean hardDelete = false;
    private boolean defaultImg = true;
    private Long itemId;
    private String category;
    private NestedItemDto userData;
    private String imgData = "-";
    private HeaderTable header;
    private PartsTable partsTable;
    private ReplacersTable replacersTable;
    private List<NestedItemDto> possibleParts = new ArrayList<>();
    private List<NestedItemDto> possibleReplacers = new ArrayList<>();
    private List<String> allCategories = new ArrayList<>();
    private Set<Long> idsToRemove = new HashSet<>();
    private List<String> messages = new ArrayList<>();
    private Long creatorId;
    private String creatorName;
    private Set<Long> wishListIds = new HashSet<>();
    private LikeListDto likeList;
    private String wikiLink;
    private String sellerLink;
    private String sellerLang;

}
