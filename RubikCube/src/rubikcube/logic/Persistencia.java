/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import rubikcube.util.AppContext;

/**
 *
 * @author Chris
 */
public class Persistencia {
    
    private static void verificarDirectorio(){
        File directory = new File("data");
        if (!directory.exists()){
            directory.mkdirs();
        }
    }
    
    public static boolean existePartida(){
        File file = new File("data\\" + AppContext.getInstance().get("user"));
        return file.exists();
    }
    
    public static boolean guardarPartida(ArrayList<String> list){
        boolean resultado = false;
        ObjectOutputStream oos = null;
        try{
            verificarDirectorio();
            File file = new File("data\\" + AppContext.getInstance().get("user"));
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);
            resultado = true;
            System.out.println("Partida guardada exitosamente");
        }catch(FileNotFoundException ex){
            resultado = false;
            System.out.println("Ha ocurrido un error generando la partida, intentalo mas tarde\nError: " + ex);
        }catch(IOException ex){
            resultado = false;
            System.out.println("Ha ocurrido un error generando la partida, intentalo mas tarde\nError: " + ex);
        } finally {
                if(oos != null){
                    try {
                        oos.close();
                    } catch(IOException ex){
                        
                    }
                }
            }
        return resultado;
    }
    
    public static ArrayList<String> cargarPartida(){
        ArrayList<String> list = null;
        if(existePartida()){
            ObjectInputStream ois = null;
            try{
                File file = new File("data\\" + AppContext.getInstance().get("user"));
                ois = new ObjectInputStream(new FileInputStream(file));
                list = (ArrayList<String>) ois.readObject();
            } catch (ClassCastException | IOException | ClassNotFoundException ex) {
                System.out.println("Ha ocurrido un error cargando la partida\nError: " + ex);
            } finally {
                if(ois != null){
                    try {
                        ois.close();
                    } catch(IOException ex){

                    }
                }
            }
        }
        return list;
    }
}
