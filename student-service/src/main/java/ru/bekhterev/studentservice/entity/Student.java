package ru.bekhterev.studentservice.entity;

import lombok.*;
import ru.bekhterev.studentservice.enums.Faculty;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {

    @Id
    @Column(nullable = false, unique = true)
    private String recordBookNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Faculty faculty;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    private String fileName;
}
