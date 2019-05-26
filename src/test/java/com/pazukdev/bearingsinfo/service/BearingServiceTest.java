package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.converter.BearingConverter;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.repository.BearingRepository;
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

    @InjectMocks
    private BearingService service;
    @Mock
    private BearingRepository repository;
    @Spy
    private BearingConverter converter;

    @Test
    public void createBearing() {
        final Bearing bearing = MockData.bearing();

        doReturn(bearing).when(repository).save(any(Bearing.class));
        service.createProduct(new BearingDto());

        verify(repository, times(1)).save(any(Bearing.class));

    }

    @Test
    public void getBearingsList() {
        final Bearing bearing = MockData.bearing();

        final List<Bearing> findAllResult = new ArrayList<>();
        findAllResult.add(bearing);
        findAllResult.add(bearing);

        doReturn(findAllResult).when(repository).findAll();
        final List<BearingDto> bearingsList = service.getProductsList();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), bearingsList.size());
        for (final BearingDto bearingDto : bearingsList) {
            assertEquals(bearing.getName(), bearingDto.getName());
            assertEquals(bearing.getType(), bearingDto.getType());
            assertEquals(bearing.getRollingElement(), bearingDto.getRollingElement());
            assertEquals(bearing.getRollingElementsQuantity(), bearingDto.getRollingElementsQuantity());
        }

    }

}
