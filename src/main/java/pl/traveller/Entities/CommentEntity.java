package pl.traveller.Entities;

import javax.persistence.*;

@Entity
@Table(name = "comment", schema = "logsta1_TIM")
public class CommentEntity {
    private long id;
    private String text;
    private boolean recommended;
    private long visitId;
    private VisitEntity visitByVisitId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "recommended")
    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    @Basic
    @Column(name = "visit_id")
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

        if (id != that.id) return false;
        if (recommended != that.recommended) return false;
        if (visitId != that.visitId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (recommended ? 1 : 0);
        result = 31 * result + (int) (visitId ^ (visitId >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public VisitEntity getVisitByVisitId() {
        return visitByVisitId;
    }

    public void setVisitByVisitId(VisitEntity visitByVisitId) {
        this.visitByVisitId = visitByVisitId;
    }
}
