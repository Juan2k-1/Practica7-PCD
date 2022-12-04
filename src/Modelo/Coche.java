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
public class Coche extends Thread {

    private Tunel tunel;
    private CanvasGenerador cv;

    public Coche(Tunel tunel, CanvasGenerador cv) {
        this.tunel = tunel;
        this.cv = cv;
    }

    public void creaCoche() throws InterruptedException {
        System.out.println("Coche: " + this.getId() + " entrando al tunel");

        Random aleatorio = new Random();
        this.cv.representaCoche((int) Thread.currentThread().getId());

        Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
        this.tunel.EntraCoche((int) Thread.currentThread().getId());
        System.out.println("Estado actual del tunel: " + this.tunel.getDiccionario());

        Thread.sleep((aleatorio.nextInt(3) + 1) * 1000);
        this.tunel.SaleCoche();
        this.cv.borrarCoche();
        System.out.println("Coche: " + this.getId() + " saliendo del tunel");
    }

    @Override
    public void run() {
        try {
            creaCoche();
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
