package io.coolexplorer.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.coolexplorer.session.model.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

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
    public static class JwtTokenInfo {
        private String id;
        private Long accountId;
        private String jwtToken;
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
    public static class JwtTokenCreateRequest {
        private Long accountId;
        private String jwtToken;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtTokenParams {
        private Long accountId;
    }
}
