/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.lang.String;

/**
 *
 * @author PCristofor
 */
public class POrar {

    private String ora;
    private String luni;
    private String marti;
    private String miercuri;
    private String joi;
    private String vineri;

    public POrar(String ora, String luni, String marti, String miercuri, String joi, String vineri) {
        this.ora = ora;
        this.luni = luni;
        this.marti = marti;
        this.miercuri = miercuri;
        this.joi = joi;
        this.vineri = vineri;
    }

    public String getLuni() {
        return luni;
    }

    public String getMarti() {
        return marti;
    }

    public String getMiercuri() {
        return miercuri;
    }

    public String getJoi() {
        return joi;
    }

    public String getVineri() {
        return vineri;
    }

    public String getOra() {
        return ora;
    }

    public void setLuni(String luni) {
        this.luni = luni;
    }

    public void setMarti(String marti) {
        this.marti = marti;
    }

    public void setMiercuri(String miercuri) {
        this.miercuri = miercuri;
    }

    public void setJoi(String joi) {
        this.joi = joi;
    }

    public void setVineri(String vineri) {
        this.vineri = vineri;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

}
