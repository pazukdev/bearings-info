package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.entity.item.Item;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ChildDtoFactory {

    public static ChildItemDto createChildItemDto(final Item item) {
        final ChildItemDto childItemDto = new ChildItemDto();
        createChildDto(childItemDto, item);
        return childItemDto;
    }

    public static ReplacerDto createReplacerDto(final Item item) {
        final ReplacerDto replacerDto = new ReplacerDto();
        createChildDto(replacerDto, item);
        return replacerDto;
    }

    private static void createChildDto(final ChildDto childDto, final Item item) {
        final DtoData dtoData = createDtoData(item);

        childDto.setName(dtoData.getName());
        childDto.setItemId(dtoData.getItemId());
        childDto.setItemName(dtoData.getItemName());
        childDto.setButtonText(dtoData.getButtonName());
        childDto.setSelectText(dtoData.getSelectText());
    }

    private static DtoData createDtoData(final Item item) {
        return new DtoData(item);
    }

}
