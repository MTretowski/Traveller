package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_role", schema = "tim", catalog = "")
public class UserRoleEntity {
    private long id;
    private String label;
    private Collection<UserEntity> usersById;

    @Id
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return id == that.id &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, label);
    }

    @OneToMany(mappedBy = "userRoleByUserRoleId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }
}
