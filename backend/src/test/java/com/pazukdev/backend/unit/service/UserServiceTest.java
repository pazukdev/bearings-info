package com.pazukdev.backend.unit.service;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.service.UserService;
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
public class UserServiceTest {

    private MockData mockData = new MockData();
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Spy
    private UserConverter converter = new UserConverter(mockData.getTestContext().getModelMapper());

    @Test
    public void createUser() {
        final UserEntity user = mockData.user();

        doReturn(user).when(repository).save(any(UserEntity.class));
        service.create(mockData.userDto());

        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void delete() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.user());

        service.delete(id);
        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void userExistsById() throws ProductNotFoundException {
        doReturn(true).when(repository).existsById(any(Long.class));
        service.entityExists(1L);
        verify(repository, times(1)).existsById(any(Long.class));
    }

    @Test
    public void getById() throws ProductNotFoundException {
        final Long id = 1L;
        doReturn(true).when(repository).existsById(any(Long.class));
        when(repository.getOne(id)).thenReturn(mockData.user());
        service.getOne(id);

        verify(repository, times(1)).getOne((any(Long.class)));
    }

    //@Test
    public void findByName() {
        doReturn(mockData.user()).when(repository).findByName(any(String.class));
        service.findByName("name");

        verify(repository, times(1)).findByName(any(String.class));
    }

    @Test
    public void findAllUsers() {
        final UserEntity user = mockData.user();

        final List<UserEntity> findAllResult = new ArrayList<>();
        findAllResult.add(user);
        findAllResult.add(user);

        doReturn(findAllResult).when(repository).findAll();
        final List<UserEntity> users = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(findAllResult.size(), users.size());
    }

}
