/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.Tunel;
import Vista.CanvasGenerador;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juald
 */
public class Furgo implements Runnable {

    private Tunel tunel;
    private CanvasGenerador cv;

    public Furgo(Tunel tunel, CanvasGenerador cv) {
        this.tunel = tunel;
        this.cv = cv;
    }

    public void creaFurgo() throws InterruptedException {
        System.out.println("Furgoneta: " + Thread.currentThread().getId() + " entrando al tunel");

        Random aleatorio = new Random();
        this.cv.representaFurgo((int) Thread.currentThread().getId());

        Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
        this.tunel.EntraFurgo((int) Thread.currentThread().getId());
        System.out.println("Estado actual del tunel: " + this.tunel.getDiccionario());

        Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
        this.tunel.SaleFurgo();
        this.cv.borrarFurgo();
        System.out.println("Furgoneta: " + Thread.currentThread().getId() + " saliendo del tunel");
    }

    @Override
    public void run() {
        try {
            creaFurgo();
        } catch (InterruptedException ex) {
            Logger.getLogger(Furgo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
