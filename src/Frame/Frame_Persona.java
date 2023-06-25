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
import proyecto_biblioteca.PersonaBeans;

/**
 *
 * @author ceden
 */
public final class Frame_Persona extends javax.swing.JFrame {

    PersonaBeans persona;

    /**
     * Creates new form Frame_Persona
     */
    public Frame_Persona() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));
        initComponents();
        persona = new PersonaBeans();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height + 50;
        int width = pantalla.width;
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        setResizable(false);
        ID_Persona.setFocusable(false);
        this.mostrar();
        // Agrega el MouseListener a la tabla Persona_Tabla
        Persona_Tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = Persona_Tabla.rowAtPoint(e.getPoint());
                int columnaSeleccionada = 0; // Primera columna
                int columnparaComparar = Persona_Tabla.columnAtPoint(e.getPoint());
                int columnaUltima = Persona_Tabla.getColumnCount() - 1;
                if (columnparaComparar == columnaUltima) {
                    Object valorUltimaColumna = Persona_Tabla.getValueAt(filaSeleccionada, columnaSeleccionada);
                    nuevoMetodo(valorUltimaColumna.toString());
                } else {
                    Object valorSeleccionado = Persona_Tabla.getValueAt(filaSeleccionada, columnaSeleccionada);
                    // Utiliza el valor seleccionado según tus necesidades
                    actualizarInformacion(valorSeleccionado.toString());
                    Agregar.setVisible(false);
                }
            }
        });
    }

    public void mostrar() {
    try {
        ResultSet result;
        DefaultTableModel modelo = new DefaultTableModel();
        Persona_Tabla.setModel(modelo);
        result = persona.Consulta_Persona();
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
        int lastColumnIndex = Persona_Tabla.getColumnCount() - 1;
        Persona_Tabla.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(columnRenderer);
    } catch (Exception e) {
    }
}

    public void nuevoMetodo(String parametro) {
        persona.setId_Persona(Integer.parseInt(parametro));
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            persona.Eliminar_Persona();
            JOptionPane.showMessageDialog(null, "El registro ha sido eliminado exitosamente.", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
            ID_Persona.setText(null);
            nuevo();
            this.mostrar();
        }
    }

    private void actualizarInformacion(String valorSeleccionado) {
        //persona = new PersonaBeans();
        persona.setId_Persona(Integer.parseInt(valorSeleccionado));
        ResultSet result = persona.Consultar_Persona_ID();
        try {
            if (result.next()) {
                ID_Persona.setText(result.getString(1));
                Cedula.setText(result.getString(2));
                Nombre.setText(result.getString(3));
                Apellido.setText(result.getString(4));
                Direccion.setText(result.getString(5));
                Telefono.setText(result.getString(6));
            }

        } catch (SQLException e) {
            System.out.println("No se pudo obtener la Persona " + e.getMessage());
        }

        mostrar();
    }

    public void nuevo() {
        Cedula.setText(null);
        Nombre.setText(null);
        Apellido.setText(null);
        Direccion.setText(null);
        Telefono.setText(null);
        Agregar.setVisible(true);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/Icono_Logo.png"));

        return retValue;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Persona_Tabla = new javax.swing.JTable();
        ActualizarMet = new javax.swing.JButton();
        Nuevo = new javax.swing.JButton();
        ID_Persona = new javax.swing.JTextField();
        Agregar = new javax.swing.JButton();
        Cedula = new javax.swing.JTextField();
        Nombre = new javax.swing.JTextField();
        Apellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Telefono = new javax.swing.JTextField();
        Direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Actualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Persona");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());

        Persona_Tabla.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Persona_Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Persona_Tabla);

        ActualizarMet.setBackground(new java.awt.Color(122, 141, 155));
        ActualizarMet.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        ActualizarMet.setForeground(new java.awt.Color(0, 33, 74));
        ActualizarMet.setText("Regresar");
        ActualizarMet.setBorderPainted(false);
        ActualizarMet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarMetActionPerformed(evt);
            }
        });

        Nuevo.setBackground(new java.awt.Color(122, 141, 155));
        Nuevo.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Nuevo.setForeground(new java.awt.Color(0, 33, 74));
        Nuevo.setText("Nuevo");
        Nuevo.setBorderPainted(false);
        Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoActionPerformed(evt);
            }
        });

        ID_Persona.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        Agregar.setBackground(new java.awt.Color(122, 141, 155));
        Agregar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar.setForeground(new java.awt.Color(0, 33, 74));
        Agregar.setText("Enviar");
        Agregar.setBorderPainted(false);
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Cedula.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        Nombre.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        Apellido.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setText("CÉDULA:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setText("NOMBRE:");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setText("APELLIDO:");

        Telefono.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        Direccion.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setText("TELEFONO:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setText("DIRECCION:");

        Actualizar.setBackground(new java.awt.Color(122, 141, 155));
        Actualizar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Actualizar.setForeground(new java.awt.Color(0, 33, 74));
        Actualizar.setText("Actualizar");
        Actualizar.setBorderPainted(false);
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
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(Direccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                .addComponent(Apellido, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Nombre, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Cedula, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Telefono, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(ID_Persona, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ActualizarMet, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(Nuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID_Persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(ActualizarMet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        ID_Persona.setText("" + persona.incremento());
        nuevo();
        this.mostrar();
    }//GEN-LAST:event_NuevoActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        if (!Cedula.getText().isBlank() && !Nombre.getText().isBlank() && !Apellido.getText().isBlank() && !Direccion.getText().isBlank() && !Telefono.getText().isBlank() && !ID_Persona.getText().isBlank()) {
            persona.setId_Persona(Integer.parseInt(ID_Persona.getText()));
            persona.setCedula(Cedula.getText());
            persona.setNombre(Nombre.getText());
            persona.setApellido(Apellido.getText());
            persona.setDireccion(Direccion.getText());
            persona.setTelefono(Telefono.getText());

            persona.insertar_Persona();
            ID_Persona.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_AgregarActionPerformed

    private void ActualizarMetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarMetActionPerformed
        Principal principal = new Principal();
        principal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActualizarMetActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        if (!Cedula.getText().isBlank() && !Nombre.getText().isBlank() && !Apellido.getText().isBlank() && !Direccion.getText().isBlank() && !Telefono.getText().isBlank() && !ID_Persona.getText().isBlank()) {
            persona.setId_Persona(Integer.parseInt(ID_Persona.getText()));
            persona.setCedula(Cedula.getText());
            persona.setNombre(Nombre.getText());
            persona.setApellido(Apellido.getText());
            persona.setDireccion(Direccion.getText());
            persona.setTelefono(Telefono.getText());

            persona.Actualizar_Persona();
            ID_Persona.setText(null);
            nuevo();
            this.mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione la persona que desea modificar.", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton ActualizarMet;
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField Apellido;
    private javax.swing.JTextField Cedula;
    private javax.swing.JTextField Direccion;
    private javax.swing.JTextField ID_Persona;
    private javax.swing.JTextField Nombre;
    private javax.swing.JButton Nuevo;
    private javax.swing.JTable Persona_Tabla;
    private javax.swing.JTextField Telefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
