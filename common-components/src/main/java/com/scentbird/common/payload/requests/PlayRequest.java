package com.scentbird.common.payload.requests;

import com.scentbird.common.stomp.StompDestinations;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PlayRequest extends StompRequest {

    private String roomId;
    private int rowIndex;
    private int columnIndex;

    @Override
    public String getDestination() {
        return StompDestinations.PLAY;
    }

}