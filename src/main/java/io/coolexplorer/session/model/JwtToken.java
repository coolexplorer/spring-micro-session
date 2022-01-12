package io.coolexplorer.session.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@RedisHash("jtwToken")
public class JwtToken implements Serializable {
    @Id
    private Long id;
    private String email;
    private String jwtToken;
    private LocalDateTime updatedTime;
}
