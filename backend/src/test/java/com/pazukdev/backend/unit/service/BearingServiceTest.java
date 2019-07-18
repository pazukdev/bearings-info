package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.entity.product.bearing.Bearing;
import com.pazukdev.backend.repository.BearingRepository;
import com.pazukdev.backend.service.BearingService;
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
public class BearingServiceTest {

    private MockData mockData = new MockData();
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
        service.create(mockData.bearingDto());

        verify(repository, times(1)).save(any(Bearing.class));
    }

    @Test
    public void findAllBearings() {
        final Bearing bearing = mockData.bearing();

        final List<Bearing> findAllResult = new ArrayList<>();
        findAllResult.add(bearing);
        findAllResult.add(bearing);

        doReturn(findAllResult).when(repository).findAll();
        final List<Bearing> bearings = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), bearings.size());
    }

}
