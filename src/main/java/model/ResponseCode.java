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
@Table(name = "response_code")
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "error_code", length = 2, nullable = false)
    private String errorCode;
    @Column(name = "error_description", length = 255, nullable = false)
    private String errorDescription;
    @Column(name = "error_level", length = 255, nullable = false)
    private String errorLevel;

}
