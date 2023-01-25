/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Roke
 */
@XmlRootElement(name = "customImage")
public class CustomImage extends Content {

    private Byte[] bytes;

    public CustomImage() {

    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

}
