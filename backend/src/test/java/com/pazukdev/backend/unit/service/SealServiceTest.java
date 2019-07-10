package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.seal.SealDto;
import com.pazukdev.backend.entity.product.Seal;
import com.pazukdev.backend.repository.SealRepository;
import com.pazukdev.backend.service.SealService;
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
public class SealServiceTest {

    private MockData mockData = new MockData();

    @InjectMocks
    private SealService service;
    @Mock
    private SealRepository repository;
    @Spy
    private SealConverter converter;

    @Test
    public void createSeal() {
        final Seal seal = mockData.seal();

        doReturn(seal).when(repository).save(any(Seal.class));
        service.create(mockData.sealDto());

        verify(repository, times(1)).save(any(Seal.class));

    }

    @Test
    public void getSealsList() {
        final Seal seal = mockData.seal();

        final List<Seal> findAllResult = new ArrayList<>();
        findAllResult.add(seal);
        findAllResult.add(seal);

        doReturn(findAllResult).when(repository).findAll();
        final List<SealDto> dtos = service.getProductsList();

        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), dtos.size());
        for (final SealDto dto : dtos) {
            assertEquals(seal.getName(), dto.getName());
            assertEquals(seal.getRotation(), dto.getRotation());
            assertEquals(seal.getMaterial(), dto.getMaterial());
        }
    }

}
