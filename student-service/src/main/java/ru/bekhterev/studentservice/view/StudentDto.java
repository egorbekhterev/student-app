package ru.bekhterev.studentservice.view;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentDto {

    @NotEmpty
    private String recordBookNumber;
    @NotEmpty
    private String faculty;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String middleName;
    private MultipartFile image;
}
