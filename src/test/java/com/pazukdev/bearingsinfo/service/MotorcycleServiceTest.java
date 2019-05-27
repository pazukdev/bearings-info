package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.converter.MotorcycleConverter;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.entity.Motorcycle;
import com.pazukdev.bearingsinfo.repository.MotorcycleRepository;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class MotorcycleServiceTest {

    @InjectMocks
    private MotorcycleService service;
    @Mock
    private MotorcycleRepository repository;
    @Spy
    private MotorcycleConverter converter;

    @Test
    public void createMotorcycle() {
        final Motorcycle motorcycle = MockData.motorcycle();

        doReturn(motorcycle).when(repository).save(any(Motorcycle.class));
        service.create(new MotorcycleDto());

        verify(repository, times(1)).save(any(Motorcycle.class));

    }

    @Test
    public void getMotorcyclesList() {
        final Motorcycle motorcycle = MockData.motorcycle();

        final List<Motorcycle> findAllResult = new ArrayList<>();
        findAllResult.add(motorcycle);
        findAllResult.add(motorcycle);

        doReturn(findAllResult).when(repository).findAll();
        final List<MotorcycleDto> dtos = service.getProductsList();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), dtos.size());
        for (final MotorcycleDto dto : dtos) {
            assertEquals(motorcycle.getName(), dto.getName());
            assertEquals(motorcycle.getManufacturer(), dto.getManufacturer());
            assertEquals(motorcycle.getWeightG(), dto.getWeightG());
        }
    }

}
