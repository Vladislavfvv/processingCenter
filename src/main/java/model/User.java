package model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"})) // Уникальность для нескольких полей  // Уникальность для нескольких полей
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
}
