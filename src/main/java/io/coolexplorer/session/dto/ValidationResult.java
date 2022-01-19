package io.coolexplorer.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Validation Result")
public class ValidationResult {
    @Schema(example = "accountId")
    private String field;

    @Schema(example = "code")
    private String code;

    @Schema(example = "Account id is empty")
    private String description;
}
