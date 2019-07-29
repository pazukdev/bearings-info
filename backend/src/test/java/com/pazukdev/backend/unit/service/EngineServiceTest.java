package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.EngineRepository;
import com.pazukdev.backend.service.EngineService;
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
public class EngineServiceTest {

    private final MockData mockData = new MockData();

    @InjectMocks
    private EngineService service;
    @Mock
    private EngineRepository repository;
    @Spy
    private EngineConverter converter = new EngineConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void create() {
        final EngineEntity engine = mockData.engine();

        doReturn(engine).when(repository).save(any(EngineEntity.class));
        service.create(mockData.engineDto());

        verify(repository, times(1)).save(any(EngineEntity.class));

    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.engine());
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
        when(repository.getOne(id)).thenReturn(mockData.engine());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.engine()).when(repository).findByName(any(String.class));
        final DefaultSearchRequest searchRequest = new DefaultSearchRequest();
        searchRequest.setName("name");
        service.search(searchRequest);

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final EngineEntity engine = mockData.engine();

        final List<EngineEntity> findAllResult = new ArrayList<>();
        findAllResult.add(engine);
        findAllResult.add(engine);

        doReturn(findAllResult).when(repository).findAll();
        final List<EngineEntity> engines = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), engines.size());
    }

}
