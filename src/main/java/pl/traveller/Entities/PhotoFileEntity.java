package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "photo_file", schema = "tim")
public class PhotoFileEntity {
    private long id;
    private byte[] file;
    private Collection<PhotoEntity> photosById;

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
    @Column(name = "file", nullable = false)
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @OneToMany(mappedBy = "photoFileByPhotoFileId")
    public Collection<PhotoEntity> getPhotosById() {
        return photosById;
    }

    public void setPhotosById(Collection<PhotoEntity> photosById) {
        this.photosById = photosById;
    }
}
