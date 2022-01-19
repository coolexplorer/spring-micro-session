package io.coolexplorer.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.coolexplorer.session.model.JwtToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class JwtTokenDTO {

    public JwtTokenDTO() {
        throw new IllegalStateException("JwtTokenDTO");
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Info")
    public static class JwtTokenInfo {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;
        @Schema(example = "1L")
        private Long accountId;
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.....")
        private String jwtToken;
        @Schema(example = "2021-01-01T00:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;

        public static JwtTokenInfo from(JwtToken jwtToken, ModelMapper modelMapper) {
            return modelMapper.map(jwtToken, JwtTokenInfo.class);
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Creation Request")
    public static class JwtTokenCreateRequest {
        @Schema(example = "1L")
        @NotNull(message = "{account.id.empty}")
        private Long accountId;
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.....")
        @NotBlank(message = "{token.value.empty}")
        private String jwtToken;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "JwtToken Search Param")
    public static class JwtTokenSearchParams {
        @Schema(example = "1L")
        @NotNull(message = "{account.id.empty}")
        private Long accountId;
    }
}
