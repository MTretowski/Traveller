package pl.traveller.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class CommentDTO {

    private String text;
    private boolean recommended;
    private Timestamp visitDate;
}
