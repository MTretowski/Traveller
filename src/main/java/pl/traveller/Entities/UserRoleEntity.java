package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user_role", schema = "tim")
public class UserRoleEntity {
    private long id;
    private String label;
    private Collection<UserEntity> usersById;

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
    @Column(name = "label", nullable = false, length = 30)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @OneToMany(mappedBy = "userRoleByUserRoleId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }
}
