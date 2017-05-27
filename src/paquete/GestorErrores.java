package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestorErrores {
    private final static File fichero = new File("ficheros/avisos.txt");
    private BufferedWriter bw;
    public GestorErrores(){
        try {
            bw = new BufferedWriter(new FileWriter(fichero));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void ComandoIncorrecto() throws IOException{
        bw.write("Comando Incorrecto");
        bw.flush();
    }

    public boolean insertaPersona(String[] comando){
        boolean comprobacion = true;

        return comprobacion;
    }
}
