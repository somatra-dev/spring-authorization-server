package auth.res_server.demo.service.impl;

import auth.res_server.demo.domain.Role;
import auth.res_server.demo.dto.role.CreateRole;
import auth.res_server.demo.repository.RoleRepository;
import auth.res_server.demo.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void createRole(CreateRole createRole) {
        if(roleRepository.existsByName(createRole.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");
        }

        Role role = new Role();
        role.setName(createRole.name());
        // Let JPA generate the ID
        roleRepository.save(role);
    }
}