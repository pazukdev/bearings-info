package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.WishListConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.entity.product.bearing.Bearing;
import com.pazukdev.backend.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class WishListService extends AbstractService<WishList, WishListDto> {

    public WishListService(final WishListRepository repository, final WishListConverter converter) {
        super(repository, converter);
    }

    @Override
    public WishList findByName(final DefaultSearchRequest request) {
        return ((WishListRepository) repository).findByName(request.getName());
    }

    public Boolean addItem(final Long wishListId, final Bearing bearing) {
        return repository.getOne(wishListId).getBearings().add(bearing);
    }

    public Boolean removeItem(final Long wishListId, final Long bearingToRemoveId) {
        final Set<Bearing> bearings = repository.getOne(wishListId).getBearings();
        for (final Bearing bearing : bearings) {
            if (bearing.getId().longValue() == bearingToRemoveId.longValue()) {
                return bearings.remove(bearing);
            }
        }
        return false;
    }


}
