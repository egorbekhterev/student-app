package ru.bekhterev.studentservice.view;

import lombok.Builder;
import lombok.Data;
import ru.bekhterev.studentservice.enums.Faculty;

@Data
@Builder(setterPrefix = "with")
public class StudentView {

    private String recordBookNumber;
    private Faculty faculty;
    private String lastName;
    private String firstName;
    private String middleName;
    private String filename;
}
