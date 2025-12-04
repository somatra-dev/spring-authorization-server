package auth.res_server.demo.repository;

import auth.res_server.demo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByName(String name);

    Optional<Role> findByName(String roleName);
}
