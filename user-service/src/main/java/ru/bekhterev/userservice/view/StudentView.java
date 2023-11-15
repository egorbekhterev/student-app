package ru.bekhterev.userservice.view;

import lombok.Data;
import lombok.ToString;
import ru.bekhterev.userservice.enums.Faculty;

@Data
@ToString
public class StudentView {

    private String recordBookNumber;
    private Faculty faculty;
    private String lastName;
    private String firstName;
    private String middleName;
    private String fileUrl;
}
