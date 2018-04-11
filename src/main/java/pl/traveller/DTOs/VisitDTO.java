package pl.traveller.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class VisitDTO {

    private long id;
    private Timestamp date;
    private boolean visited;
    private boolean visible;
    private long userId;
    private long placeId;

}