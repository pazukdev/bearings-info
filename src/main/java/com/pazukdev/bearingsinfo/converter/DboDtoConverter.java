package com.pazukdev.bearingsinfo.converter;

import javax.validation.constraints.NotNull;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface DboDtoConverter<Dbo, Dto> {

    Dto convertToDto(@NotNull final Dbo dbo);
    Dbo convertToDbo(@NotNull final Dto dto);

}
