/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author ceden
 */
public class LibroBeans {

    private int Id_Libro;
    private int Id_Editorial;
    private int Id_Autor;
    private String Nombre;
    private Date fecha_Publicacion;

    AccesoBD bd;

    public LibroBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Libro() {
        return Id_Libro;
    }

    public void setId_Libro(int Id_Libro) {
        this.Id_Libro = Id_Libro;
    }

    public int getId_Editorial() {
        return Id_Editorial;
    }

    public void setId_Editorial(int Id_Editorial) {
        this.Id_Editorial = Id_Editorial;
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

    public Date getFecha_Publicacion() {
        return fecha_Publicacion;
    }

    public void setFecha_Publicacion(Date fecha_Publicacion) {
        this.fecha_Publicacion = fecha_Publicacion;
    }

    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select max(Id_Libro) from libro");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return cont;
    }

    public void insertar_Libro() {
        try {
            String cadena = "Insert into Libro values ('" + this.getId_Libro() + "', '" + this.getId_Autor() + "','" + this.getId_Editorial() + "','" + this.getNombre() + "','" + this.getFecha_Publicacion() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la insercion del Autor");
        }
    }

    public ResultSet Consultar_Libro() {
        return (ResultSet) bd.consultaBD("SELECT libro.id_libro as id, autor.nombre as 'Autor', editorial.nombre as 'Editorial', libro.nombre, libro.fecha_publicacion, libro.id_libro as ' ' FROM "
                + "autor inner join (editorial inner join libro on editorial.id_editorial = libro.id_editorial) on libro.id_autor = autor.id_autor");
    }

    public ResultSet Consultar_Libro_ID() {
        return (ResultSet) bd.consultaBD("SELECT libro.id_libro as id, autor.nombre as 'Autor', editorial.nombre as 'Editorial', libro.nombre, libro.fecha_publicacion, libro.id_libro as ' ' FROM "
                + "autor inner join (editorial inner join libro on editorial.id_editorial = libro.id_editorial) on libro.id_autor = autor.id_autor WHERE Id_libro =" + this.getId_Libro() + "");
    }

    public void Actualizar_Libro() {
        try {
            String cadena = "UPDATE libro SET Id_Autor='" + this.getId_Autor() + "', id_editorial='" + this.getId_Editorial() + "', nombre='" + this.getNombre() + "', fecha_publicacion='" + this.getFecha_Publicacion() + "' WHERE Id_libro = " + this.getId_Libro();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la actualizacion de la Persona " + e.getMessage());
        }
    }

    public void Eliminar_Libro() {
        try {
            String cadena = "DELETE FROM libro WHERE Id_libro = " + this.getId_Libro();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }

    public ResultSet Consultar_Editorial() {
        return (ResultSet) bd.consultaBD("Select * from editorial");
    }

    public ResultSet Consulta_Editorial_Nombre(String dato) {
        return (ResultSet) bd.consultaBD("SELECT * FROM editorial WHERE nombre LIKE '%" + dato + "%'");
    }

    public ResultSet Consultar_Autor() {
        return (ResultSet) bd.consultaBD("Select * from autor");
    }

    public ResultSet Consulta_Autor_Nombre(String dato) {
        return (ResultSet) bd.consultaBD("SELECT * FROM autor WHERE nombre LIKE '%" + dato + "%'");
    }

    public void cerrar() {
        bd.cerrarBD();
    }

    @Override
    public String toString() {
        return "LibroBeans{" + "Id_Libro=" + Id_Libro + ", Id_Editorial=" + Id_Editorial + ", Id_Autor=" + Id_Autor + ", Nombre=" + Nombre + ", fecha_Publicacion=" + fecha_Publicacion + '}';
    }

}
