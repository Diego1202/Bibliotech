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
public class AutorBeans {
    private int Id_Autor;
    private String Nombre;
    private String Apellido;
    private String Nacionalidad;

    AccesoBD bd;
    
    public AutorBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Autor() {
        return Id_Autor;
    }

    public void setId_Autor(int Id_Autor) {
        this.Id_Autor = Id_Autor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String Nacionalidad) {
        this.Nacionalidad = Nacionalidad;
    }
    
    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select max(Id_Autor) from Autor");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return cont;
    }
    
    public void insertar_Autor() {
        try {
            String cadena = "Insert into Autor values ('" +  this.getId_Autor()+"', '"+ this.getNombre() + "','" + this.getApellido() + "','" + this.getNacionalidad() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la insercion del Autor");
        }
    }

    public ResultSet Consultar_Autor() {
        return (ResultSet) bd.consultaBD("Select * from autor");
    }

    public ResultSet Consultar_Autor_ID() {
        return (ResultSet) bd.consultaBD("SELECT * FROM autor WHERE Id_Autor =" + this.getId_Autor()+"");
    }      
    
    public void Actualizar_Autor() {
        try {
            String cadena = "UPDATE autor SET nombre='" + this.getNombre() + "', apellido='" + this.getApellido() + "', nacionalidad='" + this.getNacionalidad()+ "' WHERE Id_Autor = " + this.getId_Autor();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la actualizacion de la Persona " + e.getMessage());
        }
    }

    public void Eliminar_Autor() {
        try {
            String cadena = "DELETE FROM autor WHERE Id_Autor = " + this.getId_Autor();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }
    
    public void cerrar(){
        bd.cerrarBD();
    }
    
}
