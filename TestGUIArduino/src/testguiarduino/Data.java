package testguiarduino;

import java.io.*;
import java.util.*;

/**
 * Created by corperk on 03/07/17.
 */
public class Data implements Serializable{
    Map<String, Usuario> Datos;
    Map<String, List<Registro>> Registros;
    List<Registro> Regs;
    static private Data instance = null;

    static public Data getInstance(){
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    private Data(){
        Datos = new HashMap<>();
        Registros = new HashMap<>();
        Regs = new ArrayList<>();
    }

    public Usuario getUsuario(String id){
        if(Datos.containsKey(id)){
            return Datos.get(id);
        }
        else{
            return null;
        }
    }

    public boolean registrar( String id, Usuario u){
        if(Datos.containsKey((id)))
            return false;
        Datos.put(id, u);
        return true;
    }

    public void registrar(String id, String fecha){
        Registro n = new Registro(id, fecha);
        if(Registros.containsKey(id)){

        }
        else{
            Registros.put(id,new ArrayList<>());
        }
        Registros.get(id).add(n);
        Regs.add(n);
    }

    public void registrar(String id, Registro r){
        if(Registros.containsKey(id)){
        }
        else{
            Registros.put(id,new ArrayList<>());
        }
        Registros.get(id).add(r);
        Regs.add(r);
    }

    static public void CargarDatos(){
        Data e = null;
        try{
            String file = Data.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\data.ser";
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fin);
            e = (Data) ois.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
            e = null;
        }
        instance = e;
    }

    static public void GuardarDatos(){
        try {
            String file = Data.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\data.ser";
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(Data.getInstance());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Map<String, List<Registro>> getRegistros() {
        return Registros;
    }

    public List<Registro> getRegs() {
        return Regs;
    }
}
