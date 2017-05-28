package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

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

}
