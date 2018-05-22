package pl.traveller.Entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "photo_file", schema = "logsta1_TIM")
public class PhotoFileEntity {
    private long id;
    private byte[] file;
    private Collection<PhotoEntity> photoFilesById;

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
    @Column(name = "file")
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @OneToMany(mappedBy = "photoFileByPhotoFileId")
    public Collection<PhotoEntity> getPhotoFilesById() {
        return photoFilesById;
    }

    public void setPhotoFilesById(Collection<PhotoEntity> photoFilesById) {
        this.photoFilesById = photoFilesById;
    }
}
