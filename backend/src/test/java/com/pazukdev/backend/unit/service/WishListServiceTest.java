package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.WishListConverter;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.WishListRepository;
import com.pazukdev.backend.service.WishListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class WishListServiceTest {

    private final MockData mockData = new MockData();

    @InjectMocks
    private WishListService service;
    @Mock
    private WishListRepository repository;
    @Spy
    private WishListConverter converter = new WishListConverter(mockData.getTestContext().getModelMapper());

//    @Test
//    public void create() {
//        final WishListEntity wishList = mockData.wishList();
//
//        doReturn(wishList).when(repository).save(any(WishListEntity.class));
//        service.create(mockData.wishListDto());
//
//        verify(repository, times(1)).save(any(WishListEntity.class));
//
//    }

//    @Test
//    public void delete() throws ProductNotFoundException {
//        final Long id = 1L;
//        doReturn(true).when(repository).existsById(any(Long.class));
//        when(repository.getOne(id)).thenReturn(mockData.wishList());
//        service.delete(id);
//
//        verify(repository, times(1)).deleteById(any(Long.class));
//    }

    @Test
    public void existsById() throws ProductNotFoundException {
        doReturn(true).when(repository).existsById(any(Long.class));
        service.entityExists(1L);

        verify(repository, times(1)).existsById(any(Long.class));
    }

//    @Test
//    public void getById() throws ProductNotFoundException {
//        final Long id = 1L;
//        doReturn(true).when(repository).existsById(any(Long.class));
//        when(repository.getOne(id)).thenReturn(mockData.wishList());
//        service.getOne(id);
//
//        verify(repository, times(1)).getOne((any(Long.class)));
//    }

//    @Test
//    public void findByName() {
//        doReturn(mockData.wishList()).when(repository).findByName(any(String.class));
//        service.findByName("name");
//
//        verify(repository, times(1)).findByName(any(String.class));
//    }

//    @Test
//    public void findAll() {
//        final WishListEntity wishList = mockData.wishList();
//
//        final List<WishListEntity> findAllResult = new ArrayList<>();
//        findAllResult.add(wishList);
//        findAllResult.add(wishList);
//
//        doReturn(findAllResult).when(repository).findAll();
//        final List<WishListEntity> wishLists = service.findAll();
//        verify(repository, times(1)).findAll();
//        assertEquals(findAllResult.size(), wishLists.size());
//    }

//    @Test
//    public void addItem() {
//        final Long id = 1L;
//        when(repository.getOne(id)).thenReturn(mockData.wishList());
//        service.addItem(id, mockData.bearing());
//
//        verify(repository, times(1)).getOne((any(Long.class)));
//    }
//
//    @Test
//    public void removeItem() {
//        final Long id = 1L;
//        when(repository.getOne(id)).thenReturn(mockData.wishList());
//        service.removeItem(id, 2L);
//
//        verify(repository, times(1)).getOne((any(Long.class)));
//    }

}















