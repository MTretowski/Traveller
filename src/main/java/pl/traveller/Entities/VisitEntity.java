package pl.traveller.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "visit", schema = "tim", catalog = "")
public class VisitEntity {
    private long id;
    private Timestamp date;
    private boolean visited;
    private boolean visible;
    private long userId;
    private long placeId;
    private Collection<CommentEntity> commentsById;
    private UserEntity userByUserId;
    private PlaceEntity placeByPlaceId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "visited", nullable = false)
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Basic
    @Column(name = "visible", nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "place_id", nullable = false)
    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitEntity that = (VisitEntity) o;
        return id == that.id &&
                visited == that.visited &&
                visible == that.visible &&
                userId == that.userId &&
                placeId == that.placeId &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, visited, visible, userId, placeId);
    }

    @OneToMany(mappedBy = "visitByVisitId")
    public Collection<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PlaceEntity getPlaceByPlaceId() {
        return placeByPlaceId;
    }

    public void setPlaceByPlaceId(PlaceEntity placeByPlaceId) {
        this.placeByPlaceId = placeByPlaceId;
    }
}
