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
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;

/**
 *
 * @author Chris
 */
public class Persistencia {
    
    private static final String pathBase = "data\\";
    private static final String pathPartidasGuardadas = pathBase + "saved matches\\";
    
    private static void verificarDirectorio(){
        File directory = new File(pathPartidasGuardadas);
        if (!directory.exists()){
            directory.mkdirs();
        }
    }
    
    public static boolean existePartida(){
        File file = new File(pathPartidasGuardadas + AppContext.getInstance().get("user"));
        return file.exists();
    }
    
    public static boolean guardarPartida(ArrayList<String> list){
        boolean resultado = false;
        ObjectOutputStream oos = null;
        try{
            verificarDirectorio();
            File file = new File(pathPartidasGuardadas + AppContext.getInstance().get("user"));
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
    
    public static boolean guardarPartidaM(ArrayList<Move> list){
        boolean resultado = false;
        ObjectOutputStream oos = null;
        try{
            verificarDirectorio();
            File file = new File(pathPartidasGuardadas + AppContext.getInstance().get("user"));
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
                File file = new File(pathPartidasGuardadas + AppContext.getInstance().get("user"));
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
            
    public static Moves cargarPartidaM(){
        Moves list = null;
        if(existePartida()){
            ObjectInputStream ois = null;
            try{
                File file = new File(pathPartidasGuardadas + AppContext.getInstance().get("user"));
                ois = new ObjectInputStream(new FileInputStream(file));
                list = (Moves) ois.readObject();
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
    
    public static boolean guardarRankingMovimientos(RankingMovimientos ranking){
        boolean resultado = false;
        ObjectOutputStream oos = null;
        try{
            verificarDirectorio();
            File file = new File(pathBase + "Ranking de movimientos");
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(ranking);
            resultado = true;
            System.out.println("\nRanking de movimientos guardado exitosamente");
        }catch(FileNotFoundException ex){
            resultado = false;
            System.out.println("\nHa ocurrido un error guardando el Ranking de movimientos, intentalo mas tarde\nError: " + ex);
        }catch(IOException ex){
            resultado = false;
            System.out.println("\nHa ocurrido un error guardando el Ranking de movimientos, intentalo mas tarde\nError: " + ex);
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
    
    public static RankingMovimientos cargarRankingMovimientos(){
        RankingMovimientos rankingM = null;
        ObjectInputStream ois = null;
        try{
            File file = new File(pathBase + "Ranking de movimientos");
            if(file.exists()){
                ois = new ObjectInputStream(new FileInputStream(file));
                rankingM = (RankingMovimientos) ois.readObject();
            } else {
                rankingM = null;
            }
        } catch (ClassCastException | IOException | ClassNotFoundException ex) {
            rankingM = null;
            System.out.println("\nHa ocurrido un error cargando el Ranking de movimientos\nError: " + ex);
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch(IOException ex){
                    
                }
            }
        }
        return rankingM;
    }
    
    public static boolean guardarRankingTiempos(RankingTiempo ranking){
        boolean resultado = false;
        ObjectOutputStream oos = null;
        try{
            verificarDirectorio();
            File file = new File(pathBase + "Ranking de tiempos");
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(ranking);
            resultado = true;
            System.out.println("\nRanking de tiempos guardado exitosamente");
        }catch(FileNotFoundException ex){
            resultado = false;
            System.out.println("\nHa ocurrido un error guardando el Ranking de tiempos, intentalo mas tarde\nError: " + ex);
        }catch(IOException ex){
            resultado = false;
            System.out.println("\nHa ocurrido un error guardando el Ranking de tiempos, intentalo mas tarde\nError: " + ex);
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
    
    public static RankingTiempo cargarRankingTiempos(){
        RankingTiempo rankingT = null;
        ObjectInputStream ois = null;
        try{
            File file = new File(pathBase + "Ranking de tiempos");
            if(file.exists()){
                ois = new ObjectInputStream(new FileInputStream(file));
                rankingT = (RankingTiempo) ois.readObject();
            } else {
                rankingT = null;
            }
        } catch (ClassCastException | IOException | ClassNotFoundException ex) {
            rankingT = null;
            System.out.println("\nHa ocurrido un error cargando el Ranking de tiempos\nError: " + ex);
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch(IOException ex){
                    
                }
            }
        }
        return rankingT;
    }
}
