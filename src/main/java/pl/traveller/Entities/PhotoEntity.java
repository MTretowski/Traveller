package pl.traveller.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "photo", schema = "tim", catalog = "")
public class PhotoEntity {
    private long id;
    private Timestamp date;
    private boolean accepted;
    private long userId;
    private long placeId;
    private long photoFileId;
    private UserEntity userByUserId;
    private PlaceEntity placeByPlaceId;
    private PhotoFileEntity photoFileByPhotoFileId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "accepted", nullable = false)
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
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

    @Basic
    @Column(name = "photo_file_id", nullable = false)
    public long getPhotoFileId() {
        return photoFileId;
    }

    public void setPhotoFileId(long photoFileId) {
        this.photoFileId = photoFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoEntity that = (PhotoEntity) o;
        return id == that.id &&
                accepted == that.accepted &&
                userId == that.userId &&
                placeId == that.placeId &&
                photoFileId == that.photoFileId &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, accepted, userId, placeId, photoFileId);
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

    @ManyToOne
    @JoinColumn(name = "photo_file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PhotoFileEntity getPhotoFileByPhotoFileId() {
        return photoFileByPhotoFileId;
    }

    public void setPhotoFileByPhotoFileId(PhotoFileEntity photoFileByPhotoFileId) {
        this.photoFileByPhotoFileId = photoFileByPhotoFileId;
    }
}
