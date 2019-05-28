package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.converter.SealConverter;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.dto.seal.SealDtoFactory;
import com.pazukdev.bearingsinfo.entity.Seal;
import com.pazukdev.bearingsinfo.repository.SealRepository;
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
public class SealServiceTest {

    private final MockData mockData = new MockData();
    private final SealDtoFactory dtoFactory = new SealDtoFactory();
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
        service.create(dtoFactory.createDto());

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
