package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import com.pazukdev.backend.service.MotorcycleService;
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
public class MotorcycleServiceTest {

    private final MockData mockData = new MockData();

    @InjectMocks
    private MotorcycleService service;
    @Mock
    private MotorcycleRepository repository;
    @Spy
    private MotorcycleConverter converter = new MotorcycleConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void create() {
        final MotorcycleEntity motorcycle = mockData.motorcycle();

        doReturn(motorcycle).when(repository).save(any(MotorcycleEntity.class));
        service.create(mockData.motorcycleDto());

        verify(repository, times(1)).save(any(MotorcycleEntity.class));
    }

    //@Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        when(repository.existsById(any(Long.class))).thenReturn(true);
        when(repository.getOne(id)).thenReturn(mockData.motorcycle());
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
        when(repository.getOne(id)).thenReturn(mockData.motorcycle());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.motorcycle()).when(repository).findByName(any(String.class));
        service.findByName("name");

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final MotorcycleEntity motorcycle = mockData.motorcycle();

        final List<MotorcycleEntity> findAllResult = new ArrayList<>();
        findAllResult.add(motorcycle);
        findAllResult.add(motorcycle);

        doReturn(findAllResult).when(repository).findAll();
        final List<MotorcycleEntity> motorcycles = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), motorcycles.size());
    }

    @Test
    public void getSpeedReport() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.motorcycle());
        service.getSpeedReport(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void getFuelReport() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.motorcycle());
        service.getFuelReport(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

}



















