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
import proyecto_biblioteca.EditorialBeans;

/**
 *
 * @author ceden
 */
public class Frame_Editorial extends javax.swing.JFrame {

    EditorialBeans editorial;
    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    int height = pantalla.height - 170;
    int width = pantalla.width - 300;

    /**
     * Creates new form Frame_Editorial
     */

    public Frame_Editorial() {
        try {
            // Establecer el tema "Nimbus" como el aspecto visual
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            // Manejar cualquier excepción que ocurra al establecer el tema
            ex.printStackTrace();
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));
        initComponents();
        editorial = new EditorialBeans();
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        ID_Editorial.setFocusable(false);
        setResizable(false);
        mostrar();
        JTEditorialClickMouse();
    }

    private void JTEditorialClickMouse() {
        JTEditorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTEditorial.rowAtPoint(e.getPoint());
                int columnaSeleccionada = 0; // Primera columna
                int columnparaComparar = JTEditorial.columnAtPoint(e.getPoint());
                int columnaUltima = JTEditorial.getColumnCount() - 1;
                if (columnparaComparar == columnaUltima) {
                    Object valorUltimaColumna = JTEditorial.getValueAt(filaSeleccionada, columnaSeleccionada);
                    nuevoMetodo(valorUltimaColumna.toString());
                } else {
                    Object valorSeleccionado = JTEditorial.getValueAt(filaSeleccionada, columnaSeleccionada);
                    // Utiliza el valor seleccionado según tus necesidades
                    actualizarInformacion(valorSeleccionado.toString());
                    int h = height - 60;
                    setSize(width / 2, h / 2);
                    Agregar.setVisible(false);
                }
            }
        });
    }

    public void nuevoMetodo(String parametro) {
        editorial.setId_Editorial(Integer.parseInt(parametro));
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            editorial.Eliminar_Editorial();
            JOptionPane.showMessageDialog(null, "El registro ha sido eliminado exitosamente.", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
            ID_Editorial.setText(null);
            nuevo();
            this.mostrar();
        }
    }

    private void actualizarInformacion(String valorSeleccionado) {
        //persona = new PersonaBeans();
        editorial.setId_Editorial(Integer.parseInt(valorSeleccionado));
        ResultSet result = editorial.Consultar_Editorial_ID();
        try {
            if (result.next()) {
                ID_Editorial.setText(result.getString(1));
                Nombre_Editorial.setText(result.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("No se pudo obtener la Persona " + e.getMessage());
        }

        mostrar();
    }

    private void nuevo() {
        setSize(width / 2, height / 2);
        Agregar.setVisible(true);
        Nombre_Editorial.setText(null);
    }

    public void mostrar() {
        try {
            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            JTEditorial.setModel(modelo);
            result = editorial.Consulta_Editorial();
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
            int lastColumnIndex = JTEditorial.getColumnCount() - 1;
            JTEditorial.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(columnRenderer);
        } catch (SQLException e) {
        }
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
        JTEditorial = new javax.swing.JTable();
        Nuevo = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Actualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ID_Editorial = new javax.swing.JTextField();
        Nombre_Editorial = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registo de Editoriales");
        setIconImage(getIconImage());

        JTEditorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTEditorial);

        Nuevo.setBackground(new java.awt.Color(122, 141, 155));
        Nuevo.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Nuevo.setForeground(new java.awt.Color(0, 33, 74));
        Nuevo.setText("Nuevo");
        Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(122, 141, 155));
        jButton2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 33, 74));
        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(122, 141, 155));
        Agregar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar.setForeground(new java.awt.Color(0, 33, 74));
        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Actualizar.setBackground(new java.awt.Color(122, 141, 155));
        Actualizar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Actualizar.setForeground(new java.awt.Color(0, 33, 74));
        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 33, 74));
        jLabel1.setText("ID Editorial:");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 33, 74));
        jLabel2.setText("Nombre:");

        ID_Editorial.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ID_Editorial, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(Nombre_Editorial))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Nuevo)
                        .addGap(10, 10, 10)
                        .addComponent(Agregar)
                        .addGap(14, 14, 14)
                        .addComponent(Actualizar)
                        .addGap(20, 20, 20)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ID_Editorial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Nombre_Editorial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Frame_Libro libro = new Frame_Libro();
        libro.setVisible(true);
        this.setVisible(false);
        editorial.cerrar_Editorial();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoActionPerformed
        ID_Editorial.setText("" + editorial.incremento());
        nuevo();
    }//GEN-LAST:event_NuevoActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        if (!Nombre_Editorial.getText().isBlank()) {
            editorial.setId_Editorial(Integer.parseInt(ID_Editorial.getText()));
            editorial.setNombre(Nombre_Editorial.getText());
            editorial.insertar_Editorial();
            ID_Editorial.setText(null);
            this.nuevo();
            mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AgregarActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        if (!Nombre_Editorial.getText().isBlank() && !ID_Editorial.getText().isBlank()) {
            editorial.setId_Editorial(Integer.parseInt(ID_Editorial.getText()));
            editorial.setNombre(Nombre_Editorial.getText());
            editorial.Actualizar_Editorial();
            ID_Editorial.setText(null);
            this.nuevo();
            mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se aceptan valores NULOS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField ID_Editorial;
    private javax.swing.JTable JTEditorial;
    private javax.swing.JTextField Nombre_Editorial;
    private javax.swing.JButton Nuevo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
