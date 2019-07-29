package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.OilConverter;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.OilRepository;
import com.pazukdev.backend.service.OilService;
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
public class OilServiceTest {

    private MockData mockData = new MockData();
    @InjectMocks
    private OilService service;
    @Mock
    private OilRepository repository;
    @Spy
    private OilConverter converter = new OilConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void createOil() {
        final OilEntity oil = mockData.oil();

        doReturn(oil).when(repository).save(any(OilEntity.class));
        service.create(mockData.oilDto());

        verify(repository, times(1)).save(any(OilEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.oil());

        service.delete(id);
        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void oilExistsById() throws ProductNotFoundException {
        doReturn(true).when(repository).existsById(any(Long.class));
        service.productExists(1L);
        verify(repository, times(1)).existsById(any(Long.class));
    }

    @Test
    public void getById() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.oil());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findOilByName() {
        doReturn(mockData.oil()).when(repository).findByName(any(String.class));
        final DefaultSearchRequest searchRequest = new DefaultSearchRequest();
        searchRequest.setName("name");
        service.search(searchRequest);
        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAllOils() {
        final OilEntity oil = mockData.oil();

        final List<OilEntity> findAllResult = new ArrayList<>();
        findAllResult.add(oil);
        findAllResult.add(oil);

        doReturn(findAllResult).when(repository).findAll();
        final List<OilEntity> oils = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), oils.size());
    }

}
