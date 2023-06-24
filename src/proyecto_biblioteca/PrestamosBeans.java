/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import com.mysql.jdbc.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author ceden
 */
public class PrestamosBeans {

    private int Id_Prestamo;
    private int Id_Libro;
    private int Id_Persona;
    private Date Fecha_Prestamo;

    AccesoBD bd;

    public PrestamosBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Prestamo() {
        return Id_Prestamo;
    }

    public void setId_Prestamo(int Id_Prestamo) {
        this.Id_Prestamo = Id_Prestamo;
    }

    public int getId_Libro() {
        return Id_Libro;
    }

    public void setId_Libro(int Id_Libro) {
        this.Id_Libro = Id_Libro;
    }

    public int getId_Persona() {
        return Id_Persona;
    }

    public void setId_Persona(int Id_Persona) {
        this.Id_Persona = Id_Persona;
    }

    public Date getFecha_Prestamo() {
        return Fecha_Prestamo;
    }

    public void setFecha_Prestamo(Date Fecha_Prestamo) {
        this.Fecha_Prestamo = Fecha_Prestamo;
    }

    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select max(Id_Prestamo) from Prestamo");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return cont;
    }

    public void Insertar_Prestamo() {
        try {
            String cadena = "insert into Prestamo values ('" + this.incremento() + "', '" + this.getId_Libro() + "', '" + this.getId_Persona() + "', '" + this.getFecha_Prestamo() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
        }
    }

    public ResultSet Consultar_Prestamo_Id() {
        return (ResultSet) bd.consultaBD("SELECT * FROM prestamo WHERE Id_Prestamo=" + this.getId_Prestamo());
    }

    public ResultSet Consultar_Prestamos() {
        return (ResultSet) bd.consultaBD("SELECT prestamo.id_prestamo, libro.nombre as 'Libro', persona.nombre as 'Persona', prestamo.fecha "
                + "from libro inner join (prestamo inner join persona on persona.id_persona = prestamo.id_persona) on prestamo.id_libro = libro.id_libro");
    }

    public void Actualizar_Prestamo() {
        try {
            String cadena = "UPDATE Prestamo set Id_Libro='" + this.getId_Libro() + "', Id_Persona='" + this.getId_Persona() + "', Fecha='" + this.getFecha_Prestamo() + "' WHERE Id_Prestamos="+this.getId_Prestamo();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
        }
    }

    public void Eliminar_Prestamo() {
        try {
            String cadena = "DELETE FROM Prestamo WHERE Id_Prestamo = " + this.getId_Prestamo();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }

    public ResultSet Consultar_Prestamo() {
        return (ResultSet) bd.consultaBD("SELECT id_prestamo, fecha_publicacion FROM Prestamo");
    }

    public ResultSet Consultar_Libro_Nombre(String valor) {
        return (ResultSet) bd.consultaBD("SELECT libro.id_libro as Id, autor.nombre as 'autor', libro.nombre, libro.fecha_publicacion  FROM libro inner join autor on libro.id_autor=autor.id_autor WHERE libro.nombre LIKE '%" + valor + "%'");
    }

    public ResultSet Consultar_Persona() {
        return (ResultSet) bd.consultaBD("SELECT * FROM persona");
    }

    public ResultSet Consultar_Persona_Nombre(String valor) {
        return (ResultSet) bd.consultaBD("SELECT * FROM persona WHERE nombre LIKE '%" + valor + "%'");
    }

    public ResultSet Consultar_Persona_Cedula(String valor) {
        return (ResultSet) bd.consultaBD("SELECT * FROM persona WHERE cedula = '" + valor + "'");
    }
    
    public ResultSet Consultar_Persona_Cedula_Prestamo(String valor) {
        return (ResultSet) bd.consultaBD("SELECT prestamo.id_prestamo, prestamo.fecha FROM persona inner join prestamo on persona.id_persona=prestamo.id_persona WHERE persona.cedula =  '" + valor + "'");
    }
    
    public void cerrar_Prestamo(){
        bd.cerrarBD();
    }

    @Override
    public String toString() {
        return "PrestamosBeans{" + "Id_Prestamo=" + Id_Prestamo + ", Id_Libro=" + Id_Libro + ", Id_Persona=" + Id_Persona + ", Fecha_Prestamo=" + Fecha_Prestamo + '}';
    }

    
    
}
