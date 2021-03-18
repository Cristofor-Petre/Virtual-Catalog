/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

/**
 *
 * @author PCristofor
 */
public class PCatalog {

    private String nrelev;
    private String nume;
    private String absent;
    private String nota;
    private String data;
    private String setari;

    public PCatalog(String numar, String nume, String nota, String data, String absent, String setari) {
        this.nrelev = numar;
        this.nume = nume;
        this.nota = nota;
        this.data = data;
        this.absent = absent;
        this.setari = setari;
    }

    public PCatalog(String numar, String nume, String nota, String data) {
        this.nrelev = numar;
        this.nume = nume;
        this.nota = nota;
        this.data = data;

    }

    /**
     * @return the numar
     */
    public String getNrelev() {
        return nrelev;
    }

    public void setNrelev(String nrelev) {
        this.nrelev = nrelev;
    }

    /**
     * @return the nume
     */
    public String getNume() {
        return nume;
    }

    /**
     * @param nume the nume to set
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * @return the materie
     */
    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the absent
     */
    public String getAbsent() {
        return absent;
    }

    /**
     * @param absent the absent to set
     */
    public void setAbsent(String absent) {
        this.absent = absent;
    }

    /**
     * @return the setari
     */
    public String getSetari() {
        return setari;
    }

    /**
     * @param setari the setari to set
     */
    public void setSetari(String setari) {
        this.setari = setari;
    }

}
