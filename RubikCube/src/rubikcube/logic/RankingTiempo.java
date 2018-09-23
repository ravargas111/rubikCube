/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.io.Serializable;
import java.util.ArrayList;
import rubikcube.util.FlowController;

/**
 *
 * @author Chris
 */
public class RankingTiempo implements Serializable{
    
    public class Espacio implements Serializable{
        public String user;
        public Integer tiempo;//En segundos

        public Espacio(String user, Integer time) {
            this.user = user;
            this.tiempo = time;
        }
    }
    
    private static RankingTiempo INSTANCE = null;
    private ArrayList<Espacio> ranking = new ArrayList<>();
    
    public RankingTiempo(){
        //El singleton se encarga de cargar el ranking de tiempo la primera vez
        //Durante toda la sesion del programa solo se modificara la lista del singleton
        //Al final de la sesion, al cerrar el programa, es cuando se debe guardar nuevamente el RankingTiempo.getInstance()
        RankingTiempo rankingAux = Persistencia.cargarRankingTiempos();
        if(rankingAux!=null)
            ranking = rankingAux.getRanking();
    }
    
    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RankingTiempo();
                }
            }
        }
    }

    public static RankingTiempo getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    //Este metodo se encarga de verificar si la nueva puntuacion entra en el top 10
    public void setEspacio(String user, Integer time){
        if(ranking.size() >= 10){
            Espacio ultimo = ranking.get(ranking.size()-1);
            if(time<ultimo.tiempo){
                Espacio nuevo = new Espacio(user, time);
                ranking.remove(ultimo);
                ranking.add(nuevo);
                ordenarRanking();
            }
        } else {
            Espacio nuevo = new Espacio(user, time);
            ranking.add(nuevo);
            ordenarRanking();
        }
    }
    
    private void ordenarRanking(){
        ranking.sort((esp1, esp2) -> {
            if(esp1.tiempo.equals(esp2.tiempo)){
                return 0;
            } else if(esp1.tiempo < esp2.tiempo) {
                return -1;
            } else {
                return 1;
            }
        });
    }
    
    public ArrayList<Espacio> getRanking(){
        return this.ranking;
    }
}
