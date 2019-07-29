package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
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
    public void createMotorcycle() {
        final MotorcycleEntity motorcycle = mockData.motorcycle();

        doReturn(motorcycle).when(repository).save(any(MotorcycleEntity.class));
        service.create(mockData.motorcycleDto());

        verify(repository, times(1)).save(any(MotorcycleEntity.class));

    }

    @Test
    public void findAllMotorcycles() {
        final MotorcycleEntity motorcycle = mockData.motorcycle();

        final List<MotorcycleEntity> findAllResult = new ArrayList<>();
        findAllResult.add(motorcycle);
        findAllResult.add(motorcycle);

        doReturn(findAllResult).when(repository).findAll();
        final List<MotorcycleEntity> motorcycles = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), motorcycles.size());
    }

}
