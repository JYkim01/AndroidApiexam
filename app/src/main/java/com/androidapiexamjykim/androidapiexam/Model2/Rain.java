
package com.androidapiexamjykim.androidapiexam.Model2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable
{

    @SerializedName("3h")
    @Expose
    private String _3h;
    private final static long serialVersionUID = -201507097093165001L;

    public String get3h() {
        return _3h;
    }

    public void set3h(String _3h) {
        this._3h = _3h;
    }

    @Override
    public String toString() {
        return "Rain{" +
                "_3h=" + _3h +
                '}';
    }
}
