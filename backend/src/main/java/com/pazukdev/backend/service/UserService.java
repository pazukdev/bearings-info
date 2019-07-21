package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.User;
import com.pazukdev.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class UserService extends AbstractService<User, UserDto> {

    public UserService(UserRepository repository, UserConverter converter) {
        super(repository, converter);
    }

    @Override
    protected User findByName(final DefaultSearchRequest request) {
        return ((UserRepository) repository).findByName(request.getName());
    }

}
