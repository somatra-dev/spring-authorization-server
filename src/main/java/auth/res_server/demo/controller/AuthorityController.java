package auth.res_server.demo.controller;

import auth.res_server.demo.dto.authority.CreateAuthority;
import auth.res_server.demo.dto.authority.ResponseAuthority;
import auth.res_server.demo.service.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    public ResponseEntity<ResponseAuthority> createAuthority(@Valid @RequestBody CreateAuthority request) {
        ResponseAuthority response = authorityService.createAuthority(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseAuthority>> getAllAuthorities() {
        return ResponseEntity.ok(authorityService.getAllAuthorities());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseAuthority> getAuthorityByName(@PathVariable String name) {
        return ResponseEntity.ok(authorityService.getAuthorityByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }
}
