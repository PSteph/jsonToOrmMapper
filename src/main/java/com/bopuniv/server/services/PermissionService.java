package com.bopuniv.server.services;

import com.bopuniv.server.entities.Permission;
import com.bopuniv.server.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {

    @Autowired
    PermissionRepository repository;

    public void save(Permission permission){
        repository.save(permission);
    }

}
