package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.WishListConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.repository.WishListRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class WishListService extends AbstractService<WishListEntity, WishListDto> {

    public WishListService(final WishListRepository repository, final WishListConverter converter) {
        super(repository, converter);
    }

    @Override
    public WishListEntity findByName(final String name) {
        return ((WishListRepository) repository).findByName(name);
    }

//    public Boolean addItem(final Long wishListId, final BearingEntity bearing) {
//        return repository.getOne(wishListId).getBearings().add(bearing);
//    }
//
//    public Boolean removeItem(final Long wishListId, final Long bearingToRemoveId) {
//        final Set<BearingEntity> bearings = repository.getOne(wishListId).getBearings();
//        for (final BearingEntity bearing : bearings) {
//            if (bearing.getId().longValue() == bearingToRemoveId.longValue()) {
//                return bearings.remove(bearing);
//            }
//        }
//        return false;
//    }


}
