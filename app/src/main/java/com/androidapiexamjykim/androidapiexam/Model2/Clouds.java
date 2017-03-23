
package com.androidapiexamjykim.androidapiexam.Model2;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds implements Serializable
{

    @SerializedName("all")
    @Expose
    private Integer all;
    private final static long serialVersionUID = 8852868845898850336L;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}
