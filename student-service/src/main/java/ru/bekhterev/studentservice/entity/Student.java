package ru.bekhterev.studentservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "record-generator")
    @GenericGenerator(name = "record-generator",
            strategy = "ru.bekhterev.studentservice.generator.RecordBookNumberGenerator")
    @EqualsAndHashCode.Include
    private String recordBookNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Faculty faculty;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false, length = 40)
    private String firstName;

    @Column(nullable = false, length = 40)
    private String middleName;

    private String fileName;
}
