/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ceden
 */
public class EditorialBeans {
    private int Id_Editorial;
    private String Nombre;
    
    AccesoBD bd;

    public EditorialBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Editorial() {
        return Id_Editorial;
    }

    public void setId_Editorial(int Id_Editorial) {
        this.Id_Editorial = Id_Editorial;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select MAX(Id_Editorial) from Editorial");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta " + ex.getMessage());
        }
        return cont;
    }

    public void insertar_Editorial() {
        try {
            String cadena = "Insert into Editorial values ('" + this.getId_Editorial() + "','" + this.getNombre() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la insercion de la Persona " + e.getMessage());
        }
    }
    
    public ResultSet Consulta_Editorial(){
        return (ResultSet) bd.consultaBD("SELECT * FROM Editorial");
    }    
    
    public ResultSet Consultar_Editorial_ID() {
        return (ResultSet) bd.consultaBD("SELECT * FROM Editorial WHERE Id_Editorial =" + this.getId_Editorial()+"");
    }      
    
    public void Actualizar_Editorial() {
        try {
            String cadena = "UPDATE Editorial SET nombre='" + this.getNombre() + "' WHERE Id_Editorial=" + this.getId_Editorial();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la actualizacion de la Persona " + e.getMessage());
        }
    }

    public void Eliminar_Editorial() {
        try {
            String cadena = "DELETE FROM Editorial WHERE Id_Editorial=" + this.getId_Editorial();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }
    
    public void cerrar_Editorial(){
        bd.cerrarBD();
    }
    
}
