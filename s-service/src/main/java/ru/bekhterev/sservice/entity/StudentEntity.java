package ru.bekhterev.sservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.bekhterev.sservice.xml.Faculty;

@Entity
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(generator = "record-generator")
    @GenericGenerator(name = "record-generator",
            strategy = "ru.bekhterev.sservice.generator.RecordBookNumberGenerator")
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
