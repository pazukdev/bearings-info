package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import com.pazukdev.backend.repository.EngineRepository;
import com.pazukdev.backend.service.EngineService;
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
public class EngineServiceTest {

    private final MockData mockData = new MockData();

    @InjectMocks
    private EngineService service;
    @Mock
    private EngineRepository repository;
    @Spy
    private EngineConverter converter = new EngineConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void createEngine() {
        final EngineEntity engine = mockData.engine();

        doReturn(engine).when(repository).save(any(EngineEntity.class));
        service.create(mockData.engineDto());

        verify(repository, times(1)).save(any(EngineEntity.class));

    }

    @Test
    public void findAllEngines() {
        final EngineEntity engine = mockData.engine();

        final List<EngineEntity> findAllResult = new ArrayList<>();
        findAllResult.add(engine);
        findAllResult.add(engine);

        doReturn(findAllResult).when(repository).findAll();
        final List<EngineEntity> engines = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), engines.size());
    }

}
