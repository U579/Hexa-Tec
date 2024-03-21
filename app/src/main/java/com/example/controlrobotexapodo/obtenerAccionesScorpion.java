package com.example.controlrobotexapodo;

import org.json.JSONObject;

public class obtenerAccionesScorpion {

    private JSONObject json;

    obtenerAccionesScorpion(){}

    obtenerAccionesScorpion(JSONObject json){
        this.json = json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
