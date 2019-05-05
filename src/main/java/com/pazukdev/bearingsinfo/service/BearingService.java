package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.BearingConverter;
import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;
import com.pazukdev.bearingsinfo.repository.BearingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService {

    private final BearingRepository repository;
    private final BearingConverter converter;

    @Autowired
    public BearingService(final BearingRepository repository, final BearingConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void createBearing(final BearingDto bearingDto) {
        final Bearing bearing = converter.convertToDbo(bearingDto);
        repository.save(bearing);
    }

    public List<BearingDto> getBearingsList() {
        return repository.findAll().stream().map(converter::convertToDto).collect(Collectors.toList());
    }
    
}
