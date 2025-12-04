package auth.res_server.demo.service;

import auth.res_server.demo.dto.role.CreateRole;
import auth.res_server.demo.dto.role.ResponseRole;

public interface RoleService {
    void createRole(CreateRole createRole);
}
