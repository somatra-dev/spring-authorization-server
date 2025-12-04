package auth.res_server.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;


@Builder
public record CreateUser(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        String firstName,

        String lastName,

        String phoneNumber,

        String gender,

        LocalDate dob,

        Set<String> roles
) {
}
