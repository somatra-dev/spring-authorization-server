package auth.res_server.demo.controller;

import auth.res_server.demo.dto.authority.AssignAuthoritiesRequest;
import auth.res_server.demo.dto.authority.ResponseAuthority;
import auth.res_server.demo.dto.role.CreateRole;
import auth.res_server.demo.dto.role.ResponseRole;
import auth.res_server.demo.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<String> createRole(@Valid @RequestBody CreateRole role) {
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }

    @GetMapping
    public ResponseEntity<List<ResponseRole>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseRole> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @GetMapping("/{roleId}/authorities")
    public ResponseEntity<List<ResponseAuthority>> getAuthoritiesByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getAuthoritiesByRole(roleId));
    }

    @PostMapping("/{roleId}/authorities")
    public ResponseEntity<List<ResponseAuthority>> assignAuthoritiesToRole(
            @PathVariable Long roleId,
            @Valid @RequestBody AssignAuthoritiesRequest request) {
        List<ResponseAuthority> authorities = roleService.assignAuthoritiesToRole(roleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorities);
    }

    @DeleteMapping("/{roleId}/authorities/{authorityId}")
    public ResponseEntity<Void> removeAuthorityFromRole(
            @PathVariable Long roleId,
            @PathVariable Long authorityId) {
        roleService.removeAuthorityFromRole(roleId, authorityId);
        return ResponseEntity.noContent().build();
    }
}
