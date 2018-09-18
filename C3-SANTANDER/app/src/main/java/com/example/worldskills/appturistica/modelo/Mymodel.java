package com.example.worldskills.appturistica.modelo;

public class Mymodel {

    String  id, Nombre, descripcioncorta,ubicacion, descripcion, latitud, longitud, tipolugar, urlimagen;

    public Mymodel(String id, String Nombre, String descripcioncorta,
                   String ubicacion, String descripcion, String latitud, String longitud,String tipolugar, String urlimagen){
        this.id = id;
        this.Nombre = Nombre;
        this.descripcioncorta = descripcioncorta;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipolugar = tipolugar;
        this.urlimagen = urlimagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getId() {
        return id;
    }

    public String getDescripcioncorta() {
        return descripcioncorta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion(){
        return  descripcion;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getTipolugar() {
        return tipolugar;
    }

    public String getUrlimagen() {
        return urlimagen;
    }
}
