package com.pazukdev.backend.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.dto.bearing.BearingDtoFactory;
import com.pazukdev.backend.entity.Bearing;
import com.pazukdev.backend.repository.BearingRepository;
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
public class BearingServiceTest {

    private final MockData mockData = new MockData();
    private final BearingDtoFactory dtoFactory = new BearingDtoFactory();
    @InjectMocks
    private BearingService service;
    @Mock
    private BearingRepository repository;
    @Spy
    private BearingConverter converter;

    @Test
    public void createBearing() {
        final Bearing bearing = mockData.bearing();

        doReturn(bearing).when(repository).save(any(Bearing.class));
        service.create(dtoFactory.createDto());

        verify(repository, times(1)).save(any(Bearing.class));
    }

    @Test
    public void getBearingsList() {
        final Bearing bearing = mockData.bearing();

        final List<Bearing> findAllResult = new ArrayList<>();
        findAllResult.add(bearing);
        findAllResult.add(bearing);

        doReturn(findAllResult).when(repository).findAll();
        final List<BearingDto> dtos = service.getProductsList();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), dtos.size());
        for (final BearingDto dto : dtos) {
            assertEquals(bearing.getName(), dto.getName());
            assertEquals(bearing.getType(), dto.getType());
            assertEquals(bearing.getRollingElement(), dto.getRollingElement());
            assertEquals(bearing.getRollingElementsQuantity(), dto.getRollingElementsQuantity());
        }
    }

}
