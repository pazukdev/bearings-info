package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.FuelReport;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReport;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemQuantity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import com.pazukdev.backend.util.ItemUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<MotorcycleEntity, MotorcycleDto> {

    private final ItemService itemService;

    public MotorcycleService(final MotorcycleRepository repository,
                             final MotorcycleConverter converter,
                             final ItemService itemService) {
        super(repository, converter);
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
    public TableDto getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        final SpeedReport report = ReportFactory.createSpeedReport(getOne(motorcycleId));
        final String maxSpeed = report.getMaxSpeedKmh().toString();
        final String[][] matrix = {{"Max speed, km/h",  maxSpeed}};
        return new TableDto("Speed report", matrix);
    }

    @Transactional
    public TableDto getFuelReport(final Long motorcycleId) throws ProductNotFoundException {
        final FuelReport report = ReportFactory.createFuelReport(getOne(motorcycleId));
        final String operationalRange = report.getOperationalRangeKm().toString();
        final String[][] matrix = {{"Operational range, km",  operationalRange}};
        return new TableDto("Fuel report", matrix);
    }

    public TableViewDto createTableView(final String userName) {
        return createTableView(getItems(userName));
    }

    public TableViewDto createTableView(final List<ItemEntity> items) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<ItemEntity> categorizedItems : ItemUtil.categorize(items)) {
            tables.add(createTable(categorizedItems));
        }
        return new TableViewDto(items.size(), tables);
    }

    public TableDto createTable(final String motorcycleName) {
        return createTable(getItems(motorcycleName));
    }

    public TableDto createTable(final List<ItemEntity> items) {
        final String tableName = items.get(0).getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ItemEntity item : items) {
            final String[] row = {
                    item.getCategory(),
                    item.getName(),
                    item.getQuantity() != null ? item.getQuantity().toString() : "0",
                    item.getId().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public List<ItemEntity> getItems(final String motorcycleName) {
        final ItemEntity item = itemService.findByName(motorcycleName);
        final List<ItemEntity> items = new ArrayList<>();
        for (final ItemQuantity itemQuantity : item.getItemQuantities()) {
            items.add(itemQuantity.getItem());
        }
        return items;
    }

}








