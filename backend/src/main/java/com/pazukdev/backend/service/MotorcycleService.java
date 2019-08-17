package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionDto;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReport;
import com.pazukdev.backend.entity.item.ItemDescriptionEntity;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<MotorcycleEntity, MotorcycleDto> {

    private final ItemDescriptionService descriptionService;
    private final ItemService itemService;

    public MotorcycleService(final MotorcycleRepository repository,
                             final MotorcycleConverter converter,
                             final ItemDescriptionService descriptionService,
                             final ItemService itemService) {
        super(repository, converter);
        this.descriptionService = descriptionService;
        this.itemService = itemService;
    }

    @Transactional
    @Override
    public List<MotorcycleEntity> findAll() {
        final List<MotorcycleEntity> motorcycles = super.findAll();
        motorcycles.sort(Comparator.comparing(MotorcycleEntity::getProductionStartYear));
        return motorcycles;
    }

    @Transactional
    @Override
    public MotorcycleEntity findByName(final String name) {
        return ((MotorcycleRepository) repository).findByName(name);
    }

    @Transactional
    public ItemEntity getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        final SpeedReport report = ReportFactory.createSpeedReport(getOne(motorcycleId));
        ItemDescriptionDto descriptionDto = new ItemDescriptionDto();
        descriptionDto.setParameter("Max speed, km/h: ");
        descriptionDto.setValue(report.getMaxSpeedKmh().toString());
        final ItemDescriptionEntity description = descriptionService.create(descriptionDto);
        final ItemDto itemDto = new ItemDto();
        itemDto.setDescriptionIds(new HashSet<>(Collections.singletonList(description.getId())));
        return itemService.create(itemDto);
    }

//    public ItemDto getFuelReport(final Long motorcycleId) throws ProductNotFoundException {
//        final FuelReport report = ReportFactory.createFuelReport(getOne(motorcycleId));
//        final String description = "Operational range, km: " + report.getOperationalRangeKm();
//        return new ItemDto(description);
//    }

}
