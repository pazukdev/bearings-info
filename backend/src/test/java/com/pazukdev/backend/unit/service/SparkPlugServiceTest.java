package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.SparkPlugConverter;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.SparkPlugRepository;
import com.pazukdev.backend.service.SparkPlugService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class SparkPlugServiceTest {

    private MockData mockData = new MockData();
    @InjectMocks
    private SparkPlugService service;
    @Mock
    private SparkPlugRepository repository;
    @Spy
    private SparkPlugConverter converter = new SparkPlugConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void create() {
        final SparkPlugEntity sparkPlug = mockData.sparkPlug();

        doReturn(sparkPlug).when(repository).save(any(SparkPlugEntity.class));
        service.create(mockData.sparkPlugDto());

        verify(repository, times(1)).save(any(SparkPlugEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.sparkPlug());
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
        when(repository.getOne(id)).thenReturn(mockData.sparkPlug());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    @Test
    public void findByName() {
        doReturn(mockData.sparkPlug()).when(repository).findByName(any(String.class));
        service.findByName("name");

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAll() {
        final SparkPlugEntity sparkPlug = mockData.sparkPlug();

        final List<SparkPlugEntity> findAllResult = new ArrayList<>();
        findAllResult.add(sparkPlug);
        findAllResult.add(sparkPlug);

        doReturn(findAllResult).when(repository).findAll();
        final List<SparkPlugEntity> sparkPlugs = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), sparkPlugs.size());
    }

}
