package pl.traveller.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceDTO {

    private long id;
    private String name;
    private String address;
    private String gps;
    private String description;
    private boolean accepted;
    private boolean active;
    private long userId;
}
