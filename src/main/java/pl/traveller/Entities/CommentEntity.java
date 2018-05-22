package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "tim", catalog = "")
public class CommentEntity {
    private long id;
    private String text;
    private boolean recommended;
    private boolean active;
    private long visitId;
    private VisitEntity visitByVisitId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "text", nullable = false, length = 254)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "recommended", nullable = false)
    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "visit_id", nullable = false)
    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id == that.id &&
                recommended == that.recommended &&
                active == that.active &&
                visitId == that.visitId &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, recommended, active, visitId);
    }

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VisitEntity getVisitByVisitId() {
        return visitByVisitId;
    }

    public void setVisitByVisitId(VisitEntity visitByVisitId) {
        this.visitByVisitId = visitByVisitId;
    }
}
