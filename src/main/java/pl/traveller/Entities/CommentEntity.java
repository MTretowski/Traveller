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

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public VisitEntity getVisitByVisitId() {
        return visitByVisitId;
    }

    public void setVisitByVisitId(VisitEntity visitByVisitId) {
        this.visitByVisitId = visitByVisitId;
    }
}
