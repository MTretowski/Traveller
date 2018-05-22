package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "tim", catalog = "")
public class UserEntity {
    private long id;
    private String username;
    private String password;
    private boolean active;
    private long userRoleId;
    private Collection<PhotoEntity> photosById;
    private Collection<PlaceEntity> placesById;
    private UserRoleEntity userRoleByUserRoleId;
    private Collection<VisitEntity> visitsById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "user_role_id", nullable = false)
    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                active == that.active &&
                userRoleId == that.userRoleId &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, password, active, userRoleId);
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<PhotoEntity> getPhotosById() {
        return photosById;
    }

    public void setPhotosById(Collection<PhotoEntity> photosById) {
        this.photosById = photosById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<PlaceEntity> getPlacesById() {
        return placesById;
    }

    public void setPlacesById(Collection<PlaceEntity> placesById) {
        this.placesById = placesById;
    }

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserRoleEntity getUserRoleByUserRoleId() {
        return userRoleByUserRoleId;
    }

    public void setUserRoleByUserRoleId(UserRoleEntity userRoleByUserRoleId) {
        this.userRoleByUserRoleId = userRoleByUserRoleId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<VisitEntity> getVisitsById() {
        return visitsById;
    }

    public void setVisitsById(Collection<VisitEntity> visitsById) {
        this.visitsById = visitsById;
    }
}
