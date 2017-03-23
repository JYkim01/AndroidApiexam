
package com.androidapiexamjykim.androidapiexam.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable{

    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("pressure")
    @Expose
    private String pressure;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("temp_min")
    @Expose
    private String tempMin;
    @SerializedName("temp_max")
    @Expose
    private String tempMax;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
