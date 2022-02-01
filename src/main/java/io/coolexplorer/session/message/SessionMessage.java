package io.coolexplorer.session.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.coolexplorer.session.model.Session;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class SessionMessage {

    public SessionMessage() {
        new IllegalStateException("SessionMessage");
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(NON_NULL)
    @Schema(description = "Session Info")
    public static class SessionInfo {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;

        @Schema(example = "1L")
        private Long accountId;

        @Schema(example = "{\"orderCount\":1}")
        private String values;

        public static SessionMessage.SessionInfo from(Session session, ModelMapper modelMapper) {
            return modelMapper.map(session, SessionMessage.SessionInfo.class);
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Creation Message")
    public static class CreateMessage {
        @Schema(example = "1L")
        private Long accountId;

        @Schema(example = "{\"orderCount\":1}")
        private String values;

        @Schema(example = "10L")
        private Long expiration;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Request Message")
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
    @Schema(description = "Session Update Message")
    public static class UpdateMessage {
        @Schema(example = "1L")
        private Long accountId;

        @Schema(example = "{\"orderCount\":1}")
        private String values;

        @Schema(example = "10L")
        private Long expiration;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Schema(description = "Session Deletion Message")
    public static class DeleteMessage {
        @Schema(example = "ff6681f0-50f8-4110-bf96-ef6cec45780e")
        private String id;

        @Schema(example = "1L")
        private Long accountId;
    }
}
