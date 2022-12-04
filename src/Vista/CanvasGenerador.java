/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author juald
 */
public class CanvasGenerador extends Canvas {

    private ArrayList<String> colaCoches;
    private ArrayList<String> colaFurgos;
    private HashMap<String, String> diccionario = null;
    private String Coche;
    private String Furgo;
    public static String TUNEL_CENTRAL = "Tunel Central";
    public static String TUNEL_IZQUIERDO = "Tunel Izquierdo";
    public static String TUNEL_DERECHO = "Tunel Derecho";

    public CanvasGenerador() {
        super();
        colaCoches = new ArrayList<>();
        colaFurgos = new ArrayList<>();

        Coche = null;
        Furgo = null;

        setBackground(Color.DARK_GRAY);
        this.setSize(1280, 768);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Image offscreen = createImage(this.getWidth(), this.getHeight());
        Graphics bg = offscreen.getGraphics();
        int separacion = 0;

        bg.setColor(Color.white);
        Font f1 = new Font("Arial", Font.BOLD, 20);
        bg.setFont(f1);

        bg.drawString("Cola Coches", 450, 170);
        bg.drawString("Cola Furgonetas", 450, 370);

        bg.drawString("Túnel Derecho", 90, 120);
        bg.fillRect(90, 140, 80, 80);

        bg.drawString("Túnel Central", 90, 270);
        bg.fillRect(90, 290, 80, 80);

        bg.drawString("Túnel Izquierdo", 90, 420);
        bg.fillRect(90, 440, 80, 80);

        for (int i = 0; i < 10; i++) {
            bg.fillRect(separacion + 450, 180, 60, 60);
            separacion += 80;
        }

        bg.setColor(Color.WHITE);
        separacion = 0;
        for (int i = 0; i < 10; i++) {
            bg.fillRect(separacion + 450, 380, 60, 60);
            separacion += 80;
        }

        bg.setColor(Color.RED);
        separacion = 0;
        for (int i = 0; i < colaCoches.size(); i++) {
            bg.drawString(colaCoches.get(i), 345 + separacion + 110, 220);
            separacion += 80;
        }

        bg.setColor(Color.RED);
        separacion = 0;
        for (int i = 0; i < colaFurgos.size(); i++) {
            bg.drawString(colaFurgos.get(i), 345 + separacion + 110, 420);
            separacion += 80;
        }

        if (Coche != null && this.diccionario.get(TUNEL_CENTRAL).equals("Coche")) {
            bg.drawString(Coche, 105, 335);
        } else if (Coche != null && this.diccionario.get(TUNEL_IZQUIERDO).equals("Coche")) {
            bg.drawString(Coche, 105, 480);
        } else if (Coche != null && this.diccionario.get(TUNEL_DERECHO).equals("Coche")) {
            bg.drawString(Coche, 105, 180);
        }

        if (Furgo != null && this.diccionario.get(TUNEL_CENTRAL).equals("Furgoneta")) {
            bg.drawString(Furgo, 105, 335);
        } else if (Furgo != null && this.diccionario.get(TUNEL_IZQUIERDO).equals("Furgoneta")) {
            bg.drawString(Furgo, 105, 480);
        }else if (Furgo != null && this.diccionario.get(TUNEL_DERECHO).equals("Furgoneta")) {
            bg.drawString(Furgo, 105, 180);
        }

        g.drawImage(offscreen, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void representaCoche(int idCoche) {
        this.colaCoches.add("C[" + idCoche + "]");
        repaint();
    }

    public void representaFurgo(int idFurgo) {
        this.colaFurgos.add("F[" + idFurgo + "]");
        repaint();
    }

    public void insertaVehiculo(int id, String tipo) {
        if (tipo.equals("Coche")) {
            Coche = "C[" + id + "]";
            this.colaCoches.remove("C[" + id + "]");
        } else {
            Furgo = "F[" + id + "]";
            this.colaFurgos.remove("F[" + id + "]");
        }
        repaint();
    }

    public void insertaTunel(int id, HashMap<String, String> diccionario) {
        this.diccionario = diccionario;
        if (this.diccionario.get(TUNEL_CENTRAL).equals("Coche")) {
            Coche = "C[" + id + "]";
            this.colaCoches.remove("C[" + id + "]");
        } else if (this.diccionario.get(TUNEL_CENTRAL).equals("Furgoneta")) {
            Furgo = "F[" + id + "]";
            this.colaFurgos.remove("F[" + id + "]");
        }

        if (this.diccionario.get(TUNEL_DERECHO).equals("Coche")) {
            Coche = "C[" + id + "]";
            this.colaCoches.remove("C[" + id + "]");
        } else if (this.diccionario.get(TUNEL_DERECHO).equals("Furgoneta")) {
            Furgo = "F[" + id + "]";
            this.colaFurgos.remove("F[" + id + "]");
        }

        if (this.diccionario.get(TUNEL_IZQUIERDO).equals("Coche")) {
            Coche = "C[" + id + "]";
            this.colaCoches.remove("C[" + id + "]");
        } else if (this.diccionario.get(TUNEL_IZQUIERDO).equals("Furgoneta")) {
            Furgo = "F[" + id + "]";
            this.colaFurgos.remove("F[" + id + "]");
        }
        repaint();
    }

    public void borrarCoche() {
        this.Coche = null;
        repaint();
    }

    public void borrarFurgo() {
        this.Furgo = null;
        repaint();
    }
}
