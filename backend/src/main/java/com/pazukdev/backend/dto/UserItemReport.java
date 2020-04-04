package com.pazukdev.backend.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserItemReport<T> {

    protected final static Logger LOGGER = LoggerFactory.getLogger(UserItemStringReport.class);

    protected Set<T> createdItems = new HashSet<>();
    protected Set<T> likedItems = new HashSet<>();
    protected Set<T> dislikedItems = new HashSet<>();

}
