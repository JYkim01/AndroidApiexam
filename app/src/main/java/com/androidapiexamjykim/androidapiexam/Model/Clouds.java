
package com.androidapiexamjykim.androidapiexam.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clouds implements Serializable{

    @SerializedName("all")
    @Expose
    private String all;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
