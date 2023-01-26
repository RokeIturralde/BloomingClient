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
@XmlRootElement(name = "customText")
public class CustomText extends Content {

    private String text;

    public CustomText() {

    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
