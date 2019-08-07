package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.ManufacturerRepository;
import com.pazukdev.backend.service.ManufacturerService;
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
public class ManufacturerServiceTest {

    private MockData mockData = new MockData();

    @InjectMocks
    private ManufacturerService service;
    @Mock
    private ManufacturerRepository repository;
    @Spy
    private ManufacturerConverter converter = new ManufacturerConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void create() {
        doReturn(mockData.manufacturer()).when(repository).save(any(ManufacturerEntity.class));
        service.create(mockData.manufacturerDto());

        verify(repository, times(1)).save(any(ManufacturerEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.manufacturer());
        service.delete(id);

        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void existsById() throws ProductNotFoundException {
        doReturn(true).when(repository).existsById(any(Long.class));
        service.entityExists(1L);

        verify(repository, times(1)).existsById(any(Long.class));
    }

    @Test
    public void getById() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.manufacturer());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.manufacturer()).when(repository).findByName(any(String.class));
        service.findByName("name");

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final ManufacturerEntity manufacturer = mockData.manufacturer();

        final List<ManufacturerEntity> findAllResult = new ArrayList<>();
        findAllResult.add(manufacturer);
        findAllResult.add(manufacturer);

        doReturn(findAllResult).when(repository).findAll();
        final List<ManufacturerEntity> manufacturers = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), manufacturers.size());
    }

}
