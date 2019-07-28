package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
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
        final BearingEntity bearing = mockData.bearing();

        doReturn(bearing).when(repository).save(any(BearingEntity.class));
        service.create(mockData.bearingDto());

        verify(repository, times(1)).save(any(BearingEntity.class));
    }

    @Test
    public void findAllBearings() {
        final BearingEntity bearing = mockData.bearing();

        final List<BearingEntity> findAllResult = new ArrayList<>();
        findAllResult.add(bearing);
        findAllResult.add(bearing);

        doReturn(findAllResult).when(repository).findAll();
        final List<BearingEntity> bearings = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), bearings.size());
    }

}
