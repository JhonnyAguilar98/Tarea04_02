/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import modelo.Personaje;

/**
 *
 * @author user
 */
public class PersonajeServicio implements IPersonajeServicio {
    
    private static List<Personaje> personajeList = new ArrayList<>();

    @Override
    public Personaje crear(Personaje personaje) {
        this.personajeList.add(personaje);
        try {
        this.almacenarEnArchivo(personaje, "C:/carpeta1/archivoPersonaje.dat");
        } catch (Exception ex) {
            throw new RuntimeException("El código ingresado ya se encuentra "
                    + "asignado al Personaje: "+ex.getMessage());
        }
        return personaje; 
   }

    @Override
    public Personaje modificar(int codigoPersonaje, Personaje personajeNuevo) {
        
        var posicion=this.buscarPosicion(this.buscarPorCodigo(codigoPersonaje));
        this.listar().get(posicion).setNombre(personajeNuevo.getNombre());
        this.listar().get(posicion).setNumeroDeEscenas(personajeNuevo.getNumeroDeEscenas());
        this.listar().get(posicion).setActor(personajeNuevo.getActor());
        this.listar().get(posicion).setPelicula(personajeNuevo.getPelicula());
        
        return personajeNuevo;
    }

    @Override
    public Personaje eliminar(int codigoPersonaje) {
        
        Personaje personaje=this.buscarPorCodigo(codigoPersonaje);
        var posicion=this.buscarPosicion(personaje);
        this.listar().remove(posicion);
        
        return personaje;  
     }

    @Override
    public Personaje buscarPorCodigo(int codigoPersonaje) {
        
        Personaje personaje=null;
        for(var b:this.personajeList){
            if(codigoPersonaje==b.getCodigo()){
                personaje=b;
                break;
            }
        }
        return personaje;
        
    }

    @Override
    public int buscarPosicion(Personaje personaje) {
        
         int posicion=-1;
        for(var b:this.personajeList){
            posicion++;
            if(b.getCodigo()==personaje.getCodigo()){
                break;
            }
        }
        return posicion;
    }
    
    @Override
    public List<Personaje> listar() {
        List<Personaje> retorno=null;
        try {
            this.personajeList=this.recuperarDeArchivo("C:/carpeta1/archivoPersonaje.obj");
        } catch (Exception ex) {
            Logger.getLogger(PersonajeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
   @Override
    public boolean almacenarEnArchivo(Personaje personaje, String rutaArchivo) throws IOException{
        ObjectOutputStream salida=null;
        var retorno=false;
        try{
            salida = new ObjectOutputStream(new FileOutputStream(new File(rutaArchivo),true));
            salida.writeObject(personaje);
            salida.close();
            retorno=true;
        }catch(Exception e1){
            System.out.println(e1.toString());
            salida.close();
        }
        
        return retorno;
    }

    @Override
    public List<Personaje> recuperarDeArchivo(String rutaArchivo) throws Exception {
        var personajeList = new ArrayList<Personaje>();
        var fis = new FileInputStream(new File(rutaArchivo));
        ObjectInputStream entrada=null;
        try{
            
            while(fis.available()>0){
                entrada = new ObjectInputStream(fis);
                Personaje personaje =(Personaje)entrada.readObject();
                personajeList.add(personaje);
            }
        }catch(Exception ex){
            entrada.close();
        }

        return personajeList;
    } 
    
}

    

   
  

 
    
    
