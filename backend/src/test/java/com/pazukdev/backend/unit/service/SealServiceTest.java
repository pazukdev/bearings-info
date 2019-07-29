package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.SealRepository;
import com.pazukdev.backend.service.SealService;
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
public class SealServiceTest {

    private MockData mockData = new MockData();

    @InjectMocks
    private SealService service;
    @Mock
    private SealRepository repository;
    @Spy
    private SealConverter converter;

    @Test
    public void create() {
        doReturn(mockData.seal()).when(repository).save(any(SealEntity.class));
        service.create(mockData.sealDto());

        verify(repository, times(1)).save(any(SealEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.seal());
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
        when(repository.getOne(id)).thenReturn(mockData.seal());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.seal()).when(repository).findByName(any(String.class));
        final DefaultSearchRequest searchRequest = new DefaultSearchRequest();
        searchRequest.setName("name");
        service.search(searchRequest);

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final SealEntity seal = mockData.seal();

        final List<SealEntity> findAllResult = new ArrayList<>();
        findAllResult.add(seal);
        findAllResult.add(seal);

        doReturn(findAllResult).when(repository).findAll();
        final List<SealEntity> seals = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), seals.size());
    }

}
