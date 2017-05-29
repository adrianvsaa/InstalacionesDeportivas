package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Set;

public class GestorErrores {
    private final static File fichero = new File("ficheros/avisos.txt");
    private BufferedWriter bw;

    public GestorErrores(){
        try {
            bw = new BufferedWriter(new FileWriter(fichero, true));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void ComandoIncorrecto() throws IOException{
        bw.write("Comando Incorrecto\n");
        bw.flush();
    }

    public boolean insertaPersona(String[] comando)throws IOException{
        boolean comprobacion = true;
        String a1 = "";
        for(int i=0; i<comando.length; i++)
            a1 += comando[i]+" ";
        String[] a2 = a1.split("\"");
        String[] a3 = a2[0].trim().split("\\s+");
        Calendar fechaNac = Calendar.getInstance();
        if(a2.length!=5||a3.length!=3) {
            comprobacion = false;
            bw.write("IP: número de elementos incorrecto\n");
            bw.flush();
            return false;
        }
        String a4[] = a2[4].trim().split("\\s+");
        if((a4.length!=2&&comando[2].equals("monitor"))||(a4.length!=3&&comando[2].equals("usuario"))){
            comprobacion = false;
            bw.write("IP: número de elementos incorrecto\n");
            bw.flush();
            return false;
        }
        fechaNac.set(Integer.parseInt(a4[0].split("/")[2]),
                Integer.parseInt(a4[0].split("/")[1]),
                Integer.parseInt(a4[0].split("/")[0]));
        Calendar fechaLimitInf = Calendar.getInstance();
        fechaLimitInf.set(1950, 1, 1);
        Calendar fechaLimitSup = Calendar.getInstance();
        fechaLimitSup.set(2020, 1, 1);
        if(comando[2].equals("monitor")&&Integer.parseInt(a4[1])>20){
            comprobacion = false;
            bw.write("IP: Número de horas incorreto\n");
        }
        else if((fechaNac.getTimeInMillis()-fechaLimitInf.getTimeInMillis())<0){
            comprobacion = false;
            bw.write("IP: Fecha incorrecta\n");
        }
        else if((fechaLimitSup.getTimeInMillis()-fechaNac.getTimeInMillis())<0){
            comprobacion = false;
            bw.write("IP: Fecha incorrecta\n");
        }
        else if(comando[2].equals("usuario")) {
            Calendar fechaAlta = Calendar.getInstance();
            Calendar f = Calendar.getInstance();
            f.set(Calendar.YEAR, f.get(Calendar.YEAR) + 80);
            long tMax = f.getTimeInMillis();
            f.set(Calendar.YEAR, f.get(Calendar.YEAR) + 5);
            long tMin = f.getTimeInMillis();

            fechaAlta.set(Integer.parseInt(a4[1].split("/")[2]),
                    Integer.parseInt(a4[1].split("/")[1]),
                    Integer.parseInt(a4[1].split("/")[0]));
            if ((fechaAlta.getTimeInMillis() - fechaNac.getTimeInMillis()) < tMin || (fechaAlta.getTimeInMillis() - fechaNac.getTimeInMillis()) > tMax) {
                comprobacion = false;
                bw.write("IP: Fecha incorrecta\n");
            } else if (Float.parseFloat(a4[2]) < 0) {
                comprobacion = false;
                bw.write("IP: Saldo incorrecto");
            }
        }
        else
            bw.write("IP: OK\n");

        bw.flush();
        return comprobacion;
    }

    public boolean asignaMonitorGrupo(String[] comando) throws IOException{
        boolean comprobacion = true;
        if(comando.length!=5){
            bw.write("AMON: Numero de elementos incorrecto\n");
            comprobacion = false;
        }
        else if(Gestor.mapaPersonas.get(comando[2])==null){
            bw.write("AMON: Monitor inexistente\n");
            comprobacion = false;
            bw.flush();
            return false;
        }
        else if(!(Gestor.mapaPersonas.get(comando[2]) instanceof Monitor)){
            bw.write("AMON: Monitor inexistente\n");
            comprobacion = false;
            bw.flush();
            return false;
        }
        else if(Gestor.mapaActividades.get(Integer.parseInt(comando[3]))==null){
            bw.write("AMON: Actividad inexistente\n");
            comprobacion = false;
            bw.flush();
            return false;
        }
        else if(!Gestor.mapaActividades.get(Integer.parseInt(comando[3])).existsGrupo(Integer.parseInt(comando[4]))){
            bw.write("AMON: Grupo inexistente\n");
            comprobacion = false;
        }
        else if(Gestor.mapaActividades.get(Integer.parseInt(comando[3])).isGrupoAsignado(Integer.parseInt(comando[4]))){
            bw.write("AMON: Grupo ya asignado\n");
            comprobacion = false;
        }
        else if(!((Monitor) Gestor.mapaPersonas.get(comando[2])).isAsignable(Integer.parseInt(comando[3]), Integer.parseInt(comando[4]))){
            bw.write("AMON: Horas asignables mayor al máximo\n");
            comprobacion = false;
        }
        else if(((Monitor) Gestor.mapaPersonas.get(comando[2])).generaSolape(Integer.parseInt(comando[3]), Integer.parseInt(comando[4]))){
            bw.write("AMON: Se genera solape\n");
            comprobacion = false;
        }
        else
            bw.write("AMON: OK\n");
        bw.flush();
        return comprobacion;
    }

    public boolean altaUsuario(String[] comando) throws IOException{
        boolean comprobacion = true;
        if(comando.length!=4){
            bw.write("AUSER: Número de parámetros incorrecto\n");
            comprobacion = false;
        }
        else if(Gestor.mapaPersonas.get(comando[2])==null){
            bw.write("AUSER: Usuario inexistente\n");
            comprobacion = false;
        }
        else if(!(Gestor.mapaPersonas.get(comando[2]) instanceof Usuario)){
            bw.write("AUSER: Usuario inexistente\n");
            comprobacion = false;
        }
        else if(Gestor.mapaActividades.get(Integer.parseInt(comando[3]))==null){
            bw.write("AUSER: Actividad inexistente\n");
            comprobacion = false;
        }
        else if(((Usuario)Gestor.mapaPersonas.get(comando[2])).isMatriculado(Integer.parseInt(comando[3]))){
            bw.write("AUSER: Ya es usuario de la actividad indicada\n");
            comprobacion = false;
        }
        else if(!((Usuario)Gestor.mapaPersonas.get(comando[2])).isMatriculable(Integer.parseInt(comando[3]))){
            bw.write("AUSER: No cumple requisitos\n");
            comprobacion = false;
        }
        else
            bw.write("AUSER: OK\n");
        bw.flush();
        return comprobacion;
    }

    public boolean asignaGrupo(String[] comando) throws IOException{
        boolean comprobacion = true;
        if(comando.length!=5){
            bw.write("AGRUPO: numero de elementos incorrecto\n");
            comprobacion = false;
        }
        else if(Gestor.mapaPersonas.get(comando[2])==null){
            bw.write("AGRUPO: usuario inexistente\n");
            comprobacion =false;
        }
        else if(!(Gestor.mapaPersonas.get(comando[2]) instanceof Usuario)){
            bw.write("AGRUPO: usuario inexistente\n");
            comprobacion = false;
        }
        else if(Gestor.mapaActividades.get(Integer.parseInt(comando[3]))==null){
            bw.write("AGRUPO: actividad inexistente\n");
            comprobacion = false;
        }
        else if(!((Usuario) Gestor.mapaPersonas.get(comando[2])).isMatriculado(Integer.parseInt(comando[3]))){
            bw.write("AGRUPO: Usuario no dado de alta en la actividad\n");
            comprobacion = false;
        }
        else if(!Gestor.mapaActividades.get(Integer.parseInt(comando[3])).hasGrupo(Integer.parseInt(comando[4]))){
            bw.write("AGRUPO: Grupo inexistente\n");
            comprobacion = false;
        }
        else if(((Usuario) Gestor.mapaPersonas.get(comando[2])).generaSolape(Integer.parseInt(comando[3]), Integer.parseInt(comando[4]))){
            bw.write("AGRUPO: Genera solape\n");
            comprobacion = false;
        }
        else
            bw.write("AGRUPO: OK\n");
        bw.flush();
        return comprobacion;
    }

    public boolean pagoActividades(String[] comando) throws IOException{
        boolean comprobacion = true;
        File fichero = new File(comando[3]);
        if(comando.length!=4){
            bw.write("GCOBRO: numero de elemtos incorrecto\n");
            comprobacion = false;
        }
        else if(Gestor.mapaPersonas.get(comando[2])==null){
            bw.write("GCOBRO: Usuario inexistente\n");
            comprobacion =false;
        }
        else if(!((Gestor.mapaPersonas.get(comando[2]))instanceof Usuario)){
            bw.write("GCOBRO: Usuario inexistente\n");
            comprobacion = false;
        }
        else if(!fichero.exists()){
            bw.write("GCOBRO: Archivo inexistente\n");
            comprobacion = false;
        }
        else{
            bw.write("GCOBRO:\n");
            Scanner entrada = new Scanner(fichero);
            int contador =1;
            while (entrada.hasNextLine()){
                int id = Integer.parseInt(entrada.nextLine().trim());

                if(Gestor.mapaActividades.get(id)==null){
                    bw.write("\tError en linea <"+contador+">: Actividad inexistente <"+id+">\n");
                }
                else if(!((Usuario)Gestor.mapaPersonas.get(comando[2])).isMatriculado(id)){
                    bw.write("\tError en linea <"+contador+">: Usuario no dado de alta <"+id+">\n");
                }
                else if(!((Usuario)Gestor.mapaPersonas.get(comando[2])).pagar(Gestor.mapaActividades.get(id).getCoste())){
                    bw.write("\tError en linea <"+contador+">: Saldo insuficiente <"+id+">\n");
                }
                else{
                    bw.write("\tLinea <"+contador+">: OK\n");
                }
                contador++;
            }
        }
        bw.flush();
        return comprobacion;
    }

    public boolean obtenerCalendario(String[] comando) throws IOException{
        boolean comprobacion = true;
        if(Gestor.mapaPersonas.get(comando[2])==null){
            bw.write("OCALEN: usuario inexistente\n");
            comprobacion = false;
        }
        else if(!((Gestor.mapaPersonas.get(comando[2]) instanceof Usuario))){
            bw.write("OCALEN: usuario inexistente\n");
            comprobacion = false;
        }
        else if(!((Usuario) Gestor.mapaPersonas.get(comando[2])).hasAsignaciones()){
            bw.write("OCALEN: usuario sin asignaciones\n");
            comprobacion = false;
        }
        else
            bw.write("OCALEN: OK\n");
        bw.flush();
        return comprobacion;
    }

    public boolean ordenarMonitores() throws IOException{
        int x = 0;
        Set<String> keys = Gestor.mapaPersonas.keySet();
        for(String key : keys){
            if((Gestor.mapaPersonas.get(key) instanceof Monitor)){
                x++;
                break;
            }
        }
        if(x==0){
            bw.write("OMON: No hay monitores\n");
            bw.flush();
            return false;
        }
        else {
            bw.write("OMON: OK\n");
            bw.flush();
            return true;
        }
    }

    public boolean ordernarActividades() throws IOException{
        Set<Integer> keys = Gestor.mapaActividades.keySet();
        boolean comprobacion = false;
        for(int key : keys){
            if(Gestor.mapaActividades.get(key).calcularUsuarios()>0){
                comprobacion = true;
                break;
            }
        }
        if(!comprobacion){
            bw.write("OACT: No hay ningún usuario\n");
        }
        else
            bw.write("OACT: OK\n");
        bw.flush();
        return comprobacion;
    }

    public void ordenarUsuarios() throws IOException{
        bw.write("OUSER: OK");
        bw.flush();
    }

}
