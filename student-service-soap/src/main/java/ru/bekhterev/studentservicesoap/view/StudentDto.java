package ru.bekhterev.studentservicesoap.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.bekhterev.studentservicesoap.Faculty;

@Data
public class StudentDto {

    @NotEmpty
    private Faculty faculty;

    @Size(
            min = 2,
            max = 60,
            message = "Last name is required, maximum 60 characters."
    )
    @NotEmpty
    private String lastName;

    @Size(
            min = 2,
            max = 40,
            message = "First name is required, maximum 40 characters."
    )
    @NotEmpty
    private String firstName;

    @Size(
            min = 2,
            max = 40,
            message = "Middle name is required, maximum 40 characters."
    )
    @NotEmpty
    private String middleName;

    private MultipartFile image;
}
