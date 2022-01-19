package io.coolexplorer.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.coolexplorer.session.model.Session;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class SessionDTO {

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Info")
    public static class SessionInfo {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String sessionId;
        @Schema(example = "1L")
        private Long accountId;
        @Schema(example = "{\"orderCount\":1}")
        private String values;
        @Schema(example = "2021-01-01T00:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;

        public static SessionInfo from(Session session, ModelMapper modelMapper) {
            return modelMapper.map(session, SessionInfo.class)
                    .setSessionId(session.getId());
        }
    }

    public SessionDTO() {
        throw new IllegalStateException("SessionDTO");
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Creation Request")
    public static class SessionCreateRequest {
        @Schema(example = "1L")
        private Long accountId;
        @Schema(example = "{\"orderCount\":1}")
        private String values;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Search Param")
    public static class SessionSearchParams {
        @Schema(example = "1L")
        private Long accountId;
    }
}
