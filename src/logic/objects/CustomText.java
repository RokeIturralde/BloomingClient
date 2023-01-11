/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.objects;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Roke
 */
public class CustomText extends Content {

    private SimpleStringProperty text;

    public String getText() {
        return this.text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
