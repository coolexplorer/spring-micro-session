package io.coolexplorer.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
        private Long id;
        private String email;
        private String jwtToken;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class JwtTokenCreateRequest {
        private String email;
        private String jwtToken;
    }
}
