/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Roke
 */
public class User implements Serializable {

    private Integer id;
    private String email;
    private String fullName;
    private String login;
    private String password;
    private Privilege privilege;
    private Status status;
    private Date lastPasswordChange;
    private List<Album> createdAlbums;

    public List<Album> getCreatedAlbums() {
        return createdAlbums;
    }

    public void setCreatedAlbums(List<Album> createdAlbums) {
        this.createdAlbums = createdAlbums;
    }
    private List<Album> sharedAlbums;

    public List<Album> getSharedAlbums() {
        return sharedAlbums;
    }

    public void setSharedAlbums(List<Album> sharedAlbums) {
        this.sharedAlbums = sharedAlbums;
    }

    public Integer getIdUser() {
        return id;
    }

    public void setIdUser(Integer idUser) {
        this.id = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }

}
