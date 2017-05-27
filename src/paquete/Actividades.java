package paquete;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Actividades {
    private final static File fichero = new File("ficheros/actividades.txt");

    public static void poblar(){
        try{
            Scanner entrada = new Scanner(fichero);
            while(entrada.hasNextLine()) {
                String identificador = entrada.nextLine().trim();
                String nombre = entrada.nextLine().trim();
                String siglas = entrada.nextLine().trim();
                String coordinador = entrada.nextLine().trim();
                String preRequisitos = entrada.nextLine().trim();
                String duracion = entrada.nextLine().trim();
                String cost = entrada.nextLine().trim();
                cost = cost.replace(',', '.');
                String grupo = entrada.nextLine().trim();

                int id = Integer.parseInt(identificador);
                int dur = Integer.parseInt(duracion);
                float coste = Float.parseFloat(cost);
                int coord;
                if(coordinador.equals(""))
                    coord = 0;
                else
                    coord =  Integer.parseInt(coordinador);

                LinkedList<Integer> requisitos = new LinkedList<Integer>();
                LinkedList<Grupo> grupos = new LinkedList<Grupo>();
                for (int i = 0; i < preRequisitos.split(",").length; i++) {
                    if (preRequisitos.split(",")[i].trim().equals(""))
                        break;
                    requisitos.add(Integer.parseInt(preRequisitos.split(",")[i].trim()));
                }
                for (int i = 0; i < grupo.split(";").length; i++) {
                    if (grupo.split(";")[i].trim().equals(""))
                        break;
                    int idGrupo = Integer.parseInt(grupo.split(";")[i].trim().split("\\s+")[0]);
                    String dia = grupo.split(";")[i].trim().split("\\s+")[1];
                    String horaInicio = grupo.split(";")[i].trim().split("\\s+")[2];
                    String instalacion = grupo.split(";")[i].trim().split("\\s+")[3];
                    grupos.add(new Grupo(id, idGrupo, dia, horaInicio, instalacion));
                }
                Actividad a = new Actividad(id, nombre, siglas, coord, dur, coste);
                a.setRequisitos(requisitos);
                a.setGrupos(grupos);

                Gestor.mapaActividades.put(id, a);

                if(entrada.hasNextLine())
                    entrada.nextLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void imprimirActividades(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            LinkedList<Actividad> actividades = new LinkedList<Actividad>(Gestor.mapaActividades.values());

            for(int i=0; i<actividades.size(); i++){
                actividades.get(i).salidaFichero(bw);
                if((i+1)<actividades.size())
                    bw.write("************\n");
            }
           bw.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
