package com.agromercantil.gestaofrota.integration.dto;
import java.util.List;

public class FipePrice {
    private String valor;
    private String referencia;
    private String marca;
    private String modelo;
    private String anoModelo;

    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getAnoModelo() {
        return anoModelo;
    }
    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }
}