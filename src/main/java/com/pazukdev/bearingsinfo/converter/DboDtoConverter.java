package com.pazukdev.bearingsinfo.converter;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface DboDtoConverter<Dbo, Dto> {

    Dto convertToDto(final Dbo dbo);
    Dbo convertToDbo(final Dto dto);

}
