import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class tienda_abarrotes {
    private JPanel Panel1;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox cmbCategoria;
    private JComboBox cmbProveedor;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JButton INSERTARButton;
    private JButton MODIFICARButton;
    private JButton ELIMINARButton;
    private JButton BUSCARButton;
    private JButton LISTARButton;
    private JTable tablaProductos;
    private PreparedStatement ps;
    private ResultSet rs;


    public tienda_abarrotes() {
        rellenarCategorias();
        rellenarProveedores();

        //----------------------------------------------------------------------------------------------------

        INSERTARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection conn;
                try {
                    conn = getConnection();
                    System.out.println("AQUI1");
                    ps = conn.prepareStatement("INSERT INTO producto_abarrote(cod_prod, nom_prod, descrip_prod, prec_prod, cate_prod, prove_prod) VALUES(?,?,?,?,?,?)");

                    ps.setString(1,txtCodigo.getText());
                    ps.setString(2,txtNombre.getText());
                    ps.setString(3,txtDescripcion.getText());
                    ps.setString(4, txtPrecio.getText() );

                    int selec1 = cmbCategoria.getSelectedIndex();
                    ps.setString(5,  (String)cmbCategoria.getItemAt(selec1) );

                    int selec2 = cmbProveedor.getSelectedIndex();
                    ps.setString(6, (String)cmbProveedor.getItemAt(selec2));

                    int res = ps.executeUpdate();
                    System.out.println("AQUI2");
                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Producto agregado correctamente","Correcto",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al agregar el producto","Error", JOptionPane.ERROR_MESSAGE);
                    }

                    conn.close();
                    ps.close();
                    limpiarCajas();
                }catch (HeadlessException | SQLException e1){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e1);
                }
                limpiarCajas();
            }
        });

        //----------------------------------------------------------------------------------------------------

        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;

                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("SELECT * FROM producto_abarrote WHERE cod_prod='"+txtCodigo.getText()+"'");
                    rs = ps.executeQuery();

                    while( rs.next() ){
                        txtCodigo.setText( rs.getString("cod_prod") );
                        txtNombre.setText( rs.getString("nom_prod") );
                        txtDescripcion.setText( rs.getString("descrip_prod") );
                        txtPrecio.setText( rs.getString("prec_prod") );
                        cmbCategoria.setSelectedItem( rs.getString("cate_prod") );
                        cmbProveedor.setSelectedItem( rs.getString("prove_prod") );
                    }

                    conn.close();
                    ps.close();
                    rs.close();
                }catch (HeadlessException | SQLException e2){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e2);
                }
            }
        });

        //----------------------------------------------------------------------------------------------------

        LISTARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try{
                    conn = getConnection();
                    String[] titulos = {"Código","Producto","Descripción","Precio","Categoría","Proveedor"};
                    String[] registros = new String[7];
                    DefaultTableModel model = new DefaultTableModel(null, titulos);

                    ps = conn.prepareStatement("SELECT * FROM producto_abarrote");
                    rs = ps.executeQuery();

                    while( rs.next() ){
                        registros[0] = rs.getString("cod_prod");
                        registros[1] = rs.getString("nom_prod");
                        registros[2] = rs.getString("descrip_prod");
                        registros[3] = rs.getString("prec_prod");
                        registros[4] = rs.getString("cate_prod");
                        registros[5] = rs.getString("prove_prod");
                        model.addRow(registros);
                    }
                    tablaProductos.setModel(model);
                    conn.close();
                    rs.close();
                    ps.close();
                }catch (HeadlessException | SQLException e3){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e3);
                }
            }
        });

        //----------------------------------------------------------------------------------------------------

        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("DELETE FROM producto_abarrote WHERE cod_prod ='" + txtCodigo.getText() + "'");


                    int res1 = ps.executeUpdate();
                    if(res1 > 0){
                        JOptionPane.showMessageDialog(null, "Producto eliminado correctamente","Correcto",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto","Error", JOptionPane.ERROR_MESSAGE);
                    }

                    conn.close();
                    ps.close();
                    limpiarCajas();
                }catch (HeadlessException | SQLException e4){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e4);
                }
            }
        });

        //----------------------------------------------------------------------------------------------------

        MODIFICARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try {
                    conn = getConnection();
                    System.out.println("AQUI1");
                    //ps = conn.prepareStatement("INSERT INTO producto_abarrote(cod_prod, nom_prod, descrip_prod, prec_prod, cate_prod, prove_prod) VALUES(?,?,?,?,?,?)");
                    ps = conn.prepareStatement("UPDATE producto_abarrote SET cod_prod = ?, nom_prod = ?, descrip_prod = ?, prec_prod = ?, cate_prod = ?, prove_prod = ? WHERE cod_prod = '"+txtCodigo.getText()+"'");
                    ps.setString(1,txtCodigo.getText());
                    ps.setString(2,txtNombre.getText());
                    ps.setString(3,txtDescripcion.getText());
                    ps.setString(4, txtPrecio.getText() );

                    int selec1 = cmbCategoria.getSelectedIndex();
                    ps.setString(5,  (String)cmbCategoria.getItemAt(selec1) );

                    int selec2 = cmbProveedor.getSelectedIndex();
                    ps.setString(6, (String)cmbProveedor.getItemAt(selec2));

                    int res2 = ps.executeUpdate();

                    if(res2 > 0){
                        JOptionPane.showMessageDialog(null, "Producto actualizado correctamente","Correcto",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al actualizar el producto","Error", JOptionPane.ERROR_MESSAGE);
                    }

                    conn.close();
                    ps.close();
                    limpiarCajas();
                }catch (HeadlessException | SQLException e5){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e5);
                }
            }
        });
    }//FIN DEL CONSTRUCTOR

    public void limpiarCajas(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
    }

    public void rellenarCategorias(){
        Connection conn;
        conn = getConnection();
        String sql = "SELECT nom_cate from categoria_abarrote";

        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while( rs.next() ){
                cmbCategoria.addItem( rs.getString("nom_cate") );
            }

            ps.close();
            rs.close();
        }catch (HeadlessException | SQLException e1){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar las categorías");
        }
    }

    public void rellenarProveedores(){
        Connection conn;
        conn = getConnection();
        String sql = "SELECT nom_prov from proveedor";

        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while( rs.next() ){
                cmbProveedor.addItem( rs.getString("nom_prov") );
            }

            ps.close();
            rs.close();
        }catch (HeadlessException | SQLException e1){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los proveedores");
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        String base = "correccion";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "dBase123";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException | SQLException ex){
            System.out.printf("No se pudo conectar a la base de datos");
        }
        return conn;
    }



    //----------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("TIENDA DE ABARROTES");
        frame.setContentPane(new tienda_abarrotes().Panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
