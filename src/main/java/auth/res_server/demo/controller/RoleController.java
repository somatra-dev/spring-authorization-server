package auth.res_server.demo.controller;

import auth.res_server.demo.dto.role.CreateRole;
import auth.res_server.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<String> addRole(@RequestBody CreateRole role) {
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }
}
