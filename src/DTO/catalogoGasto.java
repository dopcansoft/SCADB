/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author i7
 */
public class catalogoGasto {
    	int id_catalogo_gastos;
	String concepto;
	int flag;

    public int getId_catalogo_gastos() {
        return id_catalogo_gastos;
    }

    public void setId_catalogo_gastos(int id_catologo_gastos) {
        this.id_catalogo_gastos = id_catologo_gastos;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
        
        
}
