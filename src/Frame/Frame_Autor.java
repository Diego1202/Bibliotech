/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import proyecto_biblioteca.AutorBeans;

/**
 *
 * @author ceden
 */
public class Frame_Autor extends javax.swing.JFrame {

    AutorBeans autor;

    /**
     * Creates new form Frame_Autor
     */
    public Frame_Autor() {
        try {
            // Establecer el tema "Nimbus" como el aspecto visual
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            // Manejar cualquier excepción que ocurra al establecer el tema
            ex.printStackTrace();
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));
        initComponents();
        autor = new AutorBeans();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        Id_Autor.setFocusable(false);
        this.mostrar();

        JTAutorClickMouse();

    }

    private void JTAutorClickMouse() {
        Autor_Tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = Autor_Tabla.rowAtPoint(e.getPoint());
                int columnaSeleccionada = 0; // Primera columna
                int columnparaComparar = Autor_Tabla.columnAtPoint(e.getPoint());
                int columnaUltima = Autor_Tabla.getColumnCount() - 1;
                if (columnparaComparar == columnaUltima) {
                    Object valorUltimaColumna = Autor_Tabla.getValueAt(filaSeleccionada, columnaSeleccionada);
                    nuevoMetodo(valorUltimaColumna.toString());
                } else {
                    Object valorSeleccionado = Autor_Tabla.getValueAt(filaSeleccionada, columnaSeleccionada);
                    // Utiliza el valor seleccionado según tus necesidades
                    actualizarInformacion(valorSeleccionado.toString());
                    Agregar.setVisible(false);
                }
            }
        });
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/Icono_Logo.png"));

        return retValue;
    }

    private void actualizarInformacion(String valorSeleccionado) {
        autor.setId_Autor(Integer.parseInt(valorSeleccionado));
        ResultSet result = autor.Consultar_Autor_ID();
        try {
            if (result.next()) {
                Id_Autor.setText(result.getString(1));
                Nombre.setText(result.getString(2));
                Apellido.setText(result.getString(3));
                Nacionalidad.setText(result.getString(4));
            }

        } catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion del Autor " + e.getMessage());
        }

        mostrar();
    }

    public void mostrar() {
        try {
            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            Autor_Tabla.setModel(modelo);
            result = autor.Consultar_Autor();
            ResultSetMetaData rsmd;
            rsmd = (ResultSetMetaData) result.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                modelo.addColumn(rsmd.getColumnLabel(i).toUpperCase());
            }
            while (result.next()) {
                Object[] columna = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    if ((rsmd.getColumnCount() - 1) == i) {
                        columna[i] = "Eliminar";
                    } else {
                        columna[i] = result.getObject(i + 1);
                    }
                }
                modelo.addRow(columna);
            }
            DefaultTableCellRenderer columnRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Verificar si es la última columna y establecer estilo de fuente negrita
                    int lastColumn = table.getColumnCount() - 1;
                    if (column == lastColumn) {
                        component.setFont(component.getFont().deriveFont(Font.BOLD)); // Establecer negrita
                        component.setForeground(Color.RED); // Establecer color rojo
                    } else {
                        component.setFont(table.getFont());
                        component.setForeground(table.getForeground());
                    }

                    return component;
                }
            };

            // Asignar el renderizador de celdas a la última columna
            int lastColumnIndex = Autor_Tabla.getColumnCount() - 1;
            Autor_Tabla.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(columnRenderer);
        } catch (SQLException e) {
        }
    }

    public void nuevo() {
        Nombre.setText(null);
        Apellido.setText(null);
        Nacionalidad.setText(null);
        Agregar.setVisible(true);
    }

    public void nuevoMetodo(String parametro) {
        autor.setId_Autor(Integer.parseInt(parametro));
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            autor.Eliminar_Autor();
            JOptionPane.showMessageDialog(null, "El registro ha sido eliminado exitosamente.", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
            Id_Autor.setText(null);
            nuevo();
            this.mostrar();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Regresar = new javax.swing.JButton();
        Id_Autor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Nombre = new javax.swing.JTextField();
        Apellido = new javax.swing.JTextField();
        Nacionalidad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Autor_Tabla = new javax.swing.JTable();
        Nuevo = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Actualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Libros");
        setIconImage(getIconImage());

        Regresar.setBackground(new java.awt.Color(122, 141, 155));
        Regresar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Regresar.setText("Regresar");
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setText("ID:");

        Nacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NacionalidadActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setText("NOMBRE:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setText("APELLIDO:");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setText("NACIONALIDAD:");

        Autor_Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Autor_Tabla);

        Nuevo.setBackground(new java.awt.Color(122, 141, 155));
        Nuevo.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Nuevo.setText("Nuevo");
        Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(122, 141, 155));
        Agregar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Actualizar.setBackground(new java.awt.Color(122, 141, 155));
        Actualizar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Id_Autor)
                    .addComponent(Nombre)
                    .addComponent(Apellido)
                    .addComponent(Nacionalidad))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Regresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Id_Autor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        Frame_Libro libro = new Frame_Libro();
        libro.setVisible(true);
        this.setVisible(false);
        autor.cerrar();
    }//GEN-LAST:event_RegresarActionPerformed

    private void NacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NacionalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NacionalidadActionPerformed

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        Id_Autor.setText("" + autor.incremento());
        nuevo();
        this.mostrar();
    }//GEN-LAST:event_NuevoActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        if (!Id_Autor.getText().isBlank() && !Nombre.getText().isBlank() && !Apellido.getText().isBlank() && !Nacionalidad.getText().isBlank()) {
            autor.setId_Autor(Integer.parseInt(Id_Autor.getText()));
            autor.setNombre(Nombre.getText());
            autor.setNacionalidad(Nombre.getText());
            autor.setApellido(Apellido.getText());
            autor.setNacionalidad(Nacionalidad.getText());

            autor.insertar_Autor();
            Id_Autor.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AgregarActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        if (!Id_Autor.getText().isBlank() && !Nombre.getText().isBlank() && !Apellido.getText().isBlank() && !Nacionalidad.getText().isBlank()) {
            autor.setId_Autor(Integer.parseInt(Id_Autor.getText()));
            autor.setNombre(Nombre.getText());
            autor.setNacionalidad(Nombre.getText());
            autor.setApellido(Apellido.getText());
            autor.setNacionalidad(Nacionalidad.getText());

            autor.Actualizar_Autor();
            Id_Autor.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el autor que desea modificar.", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField Apellido;
    private javax.swing.JTable Autor_Tabla;
    private javax.swing.JTextField Id_Autor;
    private javax.swing.JTextField Nacionalidad;
    private javax.swing.JTextField Nombre;
    private javax.swing.JButton Nuevo;
    private javax.swing.JButton Regresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
