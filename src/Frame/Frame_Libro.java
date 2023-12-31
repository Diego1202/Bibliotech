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
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import proyecto_biblioteca.LibroBeans;

/**
 *
 * @author ceden
 */
public final class Frame_Libro extends javax.swing.JFrame {

    LibroBeans libro;

    /**
     * Creates new form Frame_Libro
     */
    public Frame_Libro() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));

        initComponents();
        libro = new LibroBeans();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height + 25;
        int width = pantalla.width;
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        setResizable(false);
        ID_Libro.setFocusable(false);
        this.mostrar();
        comboEditoriales(Editoriales);
        comboAutores(Autores);
        JTLibroClickMouse();
    }

    private void JTLibroClickMouse() {
        JTLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTLibros.rowAtPoint(e.getPoint());
                int columnaSeleccionada = 0; // Primera columna
                int columnparaComparar = JTLibros.columnAtPoint(e.getPoint());
                int columnaUltima = JTLibros.getColumnCount() - 1;
                if (columnparaComparar == columnaUltima) {
                    Object valorUltimaColumna = JTLibros.getValueAt(filaSeleccionada, columnaSeleccionada);
                    nuevoMetodo(valorUltimaColumna.toString());
                } else {
                    Object valorSeleccionado = JTLibros.getValueAt(filaSeleccionada, columnaSeleccionada);
                    // Utiliza el valor seleccionado según tus necesidades
                    actualizarInformacion(valorSeleccionado.toString());
                    Agregar.setVisible(false);
                }
            }
        });
    }

    private void actualizarInformacion(String valorSeleccionado) {
        libro.setId_Libro(Integer.parseInt(valorSeleccionado));
        ResultSet result = libro.Consultar_Libro_ID();
        try {
            if (result.next()) {
                ID_Libro.setText(result.getString(1));
                seleccionarElemento(Editoriales, result.getString(3));
                Autores.setSelectedItem(result.getString(2));
                Nombre_Libro.setText(result.getString(4));
                Fecha_Publicacion.setDate(java.sql.Date.valueOf(result.getString(5)));
            }

        } catch (SQLException e) {
            System.out.println("No se pudo obtener la Persona " + e.getMessage());
        }

        mostrar();
    }

    private void seleccionarElemento(JComboBox combo, String nombre) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).toString().equals(nombre)) {
                combo.setSelectedItem(combo.getItemAt(i));
                break;
            }
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/Icono_Logo.png"));

        return retValue;
    }

    private void comboEditoriales(JComboBox combo) {
        try {
            ResultSet result;
            result = libro.Consultar_Editorial();
            while (result.next()) {
                combo.addItem(result.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error con el combo editorial... " + e.getMessage());
        }
    }

    private void comboAutores(JComboBox combo) {
        try {
            ResultSet result;
            result = libro.Consultar_Autor();
            while (result.next()) {
                combo.addItem(result.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error con el combo editorial... " + e.getMessage());
        }
    }

    public void mostrar() {
        try {
            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            JTLibros.setModel(modelo);
            result = libro.Consultar_Libro();
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
            // Crear renderizador de celdas personalizado
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
            int lastColumnIndex = JTLibros.getColumnCount() - 1;
            JTLibros.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(columnRenderer);
        } catch (SQLException e) {
        }
    }

    private void nuevo() {
        Agregar.setVisible(true);
        Nombre_Libro.setText(null);
        Fecha_Publicacion.setDate(null);
    }

    public void nuevoMetodo(String parametro) {
        libro.setId_Libro(Integer.parseInt(parametro));
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            libro.Eliminar_Libro();
            ID_Libro.setText(null);
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

        Regresar = new javax.swing.JToggleButton();
        Nuevo = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Editoriales = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        Nombre_Libro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Fecha_Publicacion = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        Autores = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        ID_Libro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTLibros = new javax.swing.JTable();
        Actualizar = new javax.swing.JButton();
        Agregar_Autor = new javax.swing.JButton();
        Agregar_Editorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Libros");
        setIconImage(getIconImage());

        Regresar.setBackground(new java.awt.Color(122, 141, 155));
        Regresar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Regresar.setForeground(new java.awt.Color(0, 33, 74));
        Regresar.setText("Regresar");
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });

        Nuevo.setBackground(new java.awt.Color(122, 141, 155));
        Nuevo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Nuevo.setForeground(new java.awt.Color(0, 33, 74));
        Nuevo.setText("Nuevo");
        Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(122, 141, 155));
        Agregar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Agregar.setForeground(new java.awt.Color(0, 33, 74));
        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Editoriales.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EditorialesItemStateChanged(evt);
            }
        });
        Editoriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditorialesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setText("Editorial:");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setText("Nombre:");

        Fecha_Publicacion.setDateFormatString("yyyy-MM-dd");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setText("Autor:");

        Autores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AutoresItemStateChanged(evt);
            }
        });
        Autores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoresActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setText("Fecha de Publicación:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setText("Id Libro:");

        JTLibros.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(JTLibros);

        Actualizar.setBackground(new java.awt.Color(122, 141, 155));
        Actualizar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        Actualizar.setForeground(new java.awt.Color(0, 33, 74));
        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        Agregar_Autor.setBackground(new java.awt.Color(122, 141, 155));
        Agregar_Autor.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar_Autor.setForeground(new java.awt.Color(0, 33, 74));
        Agregar_Autor.setText("Agregar Autor");
        Agregar_Autor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Agregar_AutorActionPerformed(evt);
            }
        });

        Agregar_Editorial.setBackground(new java.awt.Color(122, 141, 155));
        Agregar_Editorial.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar_Editorial.setForeground(new java.awt.Color(0, 33, 74));
        Agregar_Editorial.setText("Agregar Editorial");
        Agregar_Editorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Agregar_EditorialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Autores, 0, 307, Short.MAX_VALUE)
                    .addComponent(Nombre_Libro)
                    .addComponent(ID_Libro, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Fecha_Publicacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Editoriales, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Regresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(Actualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(Agregar_Autor)
                .addGap(63, 63, 63)
                .addComponent(Agregar_Editorial)
                .addGap(190, 190, 190))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ID_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Editoriales, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Autores, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nombre_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(Fecha_Publicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Agregar_Autor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Agregar_Editorial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        Principal principal = new Principal();
        principal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_RegresarActionPerformed

    private void EditorialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditorialesActionPerformed

    }//GEN-LAST:event_EditorialesActionPerformed

    private void AutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AutoresActionPerformed

    private void EditorialesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EditorialesItemStateChanged
        try {
            ResultSet result = libro.Consulta_Editorial_Nombre(Editoriales.getSelectedItem().toString());
            while (result.next()) {
                libro.setId_Editorial(Integer.parseInt(result.getString(1)));
            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_EditorialesItemStateChanged

    private void AutoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AutoresItemStateChanged
        try {
            ResultSet result = libro.Consulta_Autor_Nombre(Autores.getSelectedItem().toString());
            while (result.next()) {
                libro.setId_Autor(Integer.parseInt(result.getString(1)));
            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_AutoresItemStateChanged

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        ID_Libro.setText(libro.incremento() + "");
        nuevo();
    }//GEN-LAST:event_NuevoActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        if (!ID_Libro.getText().isBlank() && !Nombre_Libro.getText().isBlank() && !Fecha_Publicacion.getDate().toString().isBlank()) {
            libro.setId_Libro(Integer.parseInt(ID_Libro.getText()));
            libro.setNombre(Nombre_Libro.getText());
            String fecha = ((JTextField) Fecha_Publicacion.getDateEditor().getUiComponent()).getText();
            libro.setFecha_Publicacion(Date.valueOf(fecha));

            libro.insertar_Libro();
            ID_Libro.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AgregarActionPerformed

    private void Agregar_EditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Agregar_EditorialActionPerformed
        Frame_Editorial editorial = new Frame_Editorial();
        editorial.setVisible(true);
        this.setVisible(false);
        libro.cerrar();
    }//GEN-LAST:event_Agregar_EditorialActionPerformed

    private void Agregar_AutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Agregar_AutorActionPerformed
        Frame_Autor autor = new Frame_Autor();
        autor.setVisible(true);
        this.setVisible(false);
        libro.cerrar();
    }//GEN-LAST:event_Agregar_AutorActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        if (!ID_Libro.getText().isBlank() && !Nombre_Libro.getText().isBlank() && !Fecha_Publicacion.getDate().toString().isBlank()) {
            libro.setId_Libro(Integer.parseInt(ID_Libro.getText()));
            libro.setNombre(Nombre_Libro.getText());
            String fecha = ((JTextField) Fecha_Publicacion.getDateEditor().getUiComponent()).getText();
            libro.setFecha_Publicacion(Date.valueOf(fecha));
            System.out.println(libro.toString());
            libro.Actualizar_Libro();
            ID_Libro.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Agregar_Autor;
    private javax.swing.JButton Agregar_Editorial;
    private javax.swing.JComboBox<String> Autores;
    private javax.swing.JComboBox<String> Editoriales;
    private com.toedter.calendar.JDateChooser Fecha_Publicacion;
    private javax.swing.JTextField ID_Libro;
    private javax.swing.JTable JTLibros;
    private javax.swing.JTextField Nombre_Libro;
    private javax.swing.JButton Nuevo;
    private javax.swing.JToggleButton Regresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
