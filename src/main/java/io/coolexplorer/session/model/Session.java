package io.coolexplorer.session.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@RedisHash("session")
public class Session implements Serializable {
    @Id
    private String id;
    @Indexed
    private Long accountId;
    private String values;
    @TimeToLive
    private Long expiration;
    private LocalDateTime updatedAt;
}
