package pl.traveller.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "photo", schema = "logsta1_tim")
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
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "accepted")
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "place_id")
    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name = "photo_file_id")
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

        if (id != that.id) return false;
        if (accepted != that.accepted) return false;
        if (userId != that.userId) return false;
        if (placeId != that.placeId) return false;
        if (photoFileId != that.photoFileId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (accepted ? 1 : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (placeId ^ (placeId >>> 32));
        result = 31 * result + (int) (photoFileId ^ (photoFileId >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public PlaceEntity getPlaceByPlaceId() {
        return placeByPlaceId;
    }

    public void setPlaceByPlaceId(PlaceEntity placeByPlaceId) {
        this.placeByPlaceId = placeByPlaceId;
    }

    @ManyToOne
    @JoinColumn(name = "photo_file_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public PhotoFileEntity getPhotoFileByPhotoFileId() {
        return photoFileByPhotoFileId;
    }

    public void setPhotoFileByPhotoFileId(PhotoFileEntity photoFileByPhotoFileId) {
        this.photoFileByPhotoFileId = photoFileByPhotoFileId;
    }
}
