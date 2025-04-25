package dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardStatusDto {
    private Long id;
    private String cardStatusName;
}
