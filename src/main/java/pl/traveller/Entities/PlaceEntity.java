package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "place", schema = "tim")
public class PlaceEntity {
    private long id;
    private String name;
    private String address;
    private String gps;
    private String description;
    private boolean accepted;
    private boolean active;
    private long userId;
    private Collection<PhotoEntity> photosById;
    private UserEntity userByUserId;
    private Collection<VisitEntity> visitsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 254)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "gps", nullable = false, length = 254)
    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 254)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @OneToMany(mappedBy = "placeByPlaceId")
    public Collection<PhotoEntity> getPhotosById() {
        return photosById;
    }

    public void setPhotosById(Collection<PhotoEntity> photosById) {
        this.photosById = photosById;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @OneToMany(mappedBy = "placeByPlaceId")
    public Collection<VisitEntity> getVisitsById() {
        return visitsById;
    }

    public void setVisitsById(Collection<VisitEntity> visitsById) {
        this.visitsById = visitsById;
    }
}
