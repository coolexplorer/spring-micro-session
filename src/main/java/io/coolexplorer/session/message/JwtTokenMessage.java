package io.coolexplorer.session.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.coolexplorer.session.model.JwtToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class JwtTokenMessage {
    public JwtTokenMessage() {
        new IllegalStateException("JwtTokenMessage");
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(NON_NULL)
    @Schema(description = "JwtToken Cache Info")
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

        public static JwtTokenMessage.JwtTokenInfo from(JwtToken jwtToken, ModelMapper modelMapper) {
            return modelMapper.map(jwtToken, JwtTokenMessage.JwtTokenInfo.class);
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Cache Creation Message")
    public static class CreateMessage {
        @Schema(example = "1L")
        private Long accountId;

        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.....")
        private String jwtToken;

        @Schema(example = "10L")
        private Long expiration;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Cache Request Message")
    public static class RequestMessage {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;

        @Schema(example = "1L")
        private Long accountId;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Cache Update Message")
    public static class UpdateMessage {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;

        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.....")
        private String jwtToken;

        @Schema(example = "10L")
        private Long expiration;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "JwtToken Cache Delete Message")
    public static class DeleteMessage {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;
        @Schema(example = "1L")
        private Long accountId;
    }
}
