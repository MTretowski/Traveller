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
public class CommentDTO {

    private long id;
    private String text;
    private boolean recommended;
    private Timestamp visitDate;
    private boolean active;
    private String username;
    private long userId;
    private long visitId;
}
