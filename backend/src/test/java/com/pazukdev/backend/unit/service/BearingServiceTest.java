package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.BearingRepository;
import com.pazukdev.backend.service.BearingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class BearingServiceTest {

    private MockData mockData = new MockData();
    @InjectMocks
    private BearingService service;
    @Mock
    private BearingRepository repository;
    @Spy
    private BearingConverter converter;

    @Test
    public void create() {
        final BearingEntity bearing = mockData.bearing();

        doReturn(bearing).when(repository).save(any(BearingEntity.class));
        service.create(mockData.bearingDto());

        verify(repository, times(1)).save(any(BearingEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.bearing());

        service.delete(id);
        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void existsById() throws ProductNotFoundException {
        doReturn(true).when(repository).existsById(any(Long.class));
        service.productExists(1L);
        verify(repository, times(1)).existsById(any(Long.class));
    }

    @Test
    public void getById() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.bearing());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.bearing()).when(repository).findByName(any(String.class));
        final DefaultSearchRequest searchRequest = new DefaultSearchRequest();
        searchRequest.setName("name");
        service.search(searchRequest);
        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final BearingEntity bearing = mockData.bearing();

        final List<BearingEntity> findAllResult = new ArrayList<>();
        findAllResult.add(bearing);
        findAllResult.add(bearing);

        doReturn(findAllResult).when(repository).findAll();
        final List<BearingEntity> bearings = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), bearings.size());
    }

}
