/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.Models;

/**
 * Created by Mauricio on 27/10/15.
 */
public class ItemEntry {
    private String name;
    private String image;
    private String summary;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



    public ItemEntry(String name, String image, String summary) {
        this.name = name;
        this.image = image;
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
