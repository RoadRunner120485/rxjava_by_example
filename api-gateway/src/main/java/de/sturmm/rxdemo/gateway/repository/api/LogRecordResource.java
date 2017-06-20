package de.sturmm.rxdemo.gateway.repository.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;

/**
 * Created by sturmm on 06.06.17.
 */
@Data
@Setter(AccessLevel.PRIVATE)
public class LogRecordResource {

    private String id;

    private Instant startTimestamp;
    private Instant endTimestamp;
    private int startMileage;
    private int endMileage;

}
