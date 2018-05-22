package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "photo_file", schema = "tim", catalog = "")
public class PhotoFileEntity {
    private long id;
    private byte[] file;
    private Collection<PhotoEntity> photosById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "file", nullable = false)
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoFileEntity that = (PhotoFileEntity) o;
        return id == that.id &&
                Arrays.equals(file, that.file);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    @OneToMany(mappedBy = "photoFileByPhotoFileId")
    public Collection<PhotoEntity> getPhotosById() {
        return photosById;
    }

    public void setPhotosById(Collection<PhotoEntity> photosById) {
        this.photosById = photosById;
    }
}
