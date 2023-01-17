/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Roke
 */
public class Content implements Serializable {

    private Integer id;
    private List<Album> albums;
    private SimpleStringProperty name;
    private Date uploadDate;
    private SimpleStringProperty location;

    public String getLocation() {
        return this.location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public Integer getContentId() {
        return id;
    }

    public void setContentId(Integer id) {
        this.id = id;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Content)) {
            return false;
        }
        Content other = (Content) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Content[ id=" + id + " ]";
    }
}
