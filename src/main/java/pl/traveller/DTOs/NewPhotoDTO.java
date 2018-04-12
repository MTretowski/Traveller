package pl.traveller.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPhotoDTO {

    private byte[] file;
    private Timestamp date;
    private boolean accepted;
    private long userId;
    private long placeId;
}
