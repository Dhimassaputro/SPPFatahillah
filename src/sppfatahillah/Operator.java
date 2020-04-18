/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppfatahillah;

    import java.awt.event.KeyEvent;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.ResultSetMetaData;
    import java.sql.Statement;
    import javax.swing.JOptionPane;
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dhimas
 */
public class Operator extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private ResultSet RsOperator;
    private String sql="";

    private String pengguna, sandi, nama, tipe, status, pembuat;

    /**
     * Creates new form Operator
     */
    public Operator() {
        initComponents();
        koneksitabel();
        tampildata("SELECT * FROM operator");
    }

    private void tampildata(String sql){
        DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No");
        datalist.addColumn("Pengguna");
        datalist.addColumn("Sandi");
        datalist.addColumn("Nama");
        datalist.addColumn("Tipe");
        datalist.addColumn("Status");
        datalist.addColumn("Pembuat");
        try {
            int i = 1;
            st=con.createStatement();
            RsOperator=st.executeQuery("select * from operator");
            while (RsOperator.next()){
                datalist.addRow(new Object[]{
                    (""+i++),RsOperator.getString(1), RsOperator.getString(2), 
                    RsOperator.getString(3), RsOperator.getString(4), RsOperator.getString(5), RsOperator.getString(6)
                });
                Gridstaff.setModel(datalist);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        } 
    }
    
    private void form_awal(){
        form_disable();
        form_clear(); 
        btn_simpan.setText("Simpan");
        btn_tambah.requestFocus(true);
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(false);
        btn_batal.setEnabled(false);
        btn_hapus.setEnabled(false);   
    }
    
    private void form_disable(){
        txt_pengguna.setEnabled(false);
        txt_sandi.setEnabled(false);
        txt_nama.setEnabled(false);
        cbx_tipe.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pembuat.setEnabled(false);
    }
    
    public void disableData(){
        txt_pengguna.setEnabled(false);
        txt_sandi.setEnabled(false);
        txt_nama.setEnabled(false);
        cbx_tipe.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pembuat.setEnabled(false);
        btn_tambah.setEnabled(true);
        btn_kembali.setEnabled(false);
        btn_simpan.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_batal.setEnabled(false);
        btn_tambah.requestFocus();
    }
    
    public void enableData(){
        txt_pengguna.setEnabled(true);
        txt_sandi.setEnabled(true);
        txt_nama.setEnabled(true);
        cbx_tipe.setEnabled(true);
        txt_status.setEnabled(true);
        txt_pembuat.setEnabled(true);
        btn_tambah.setEnabled(false);
        btn_kembali.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
    }
    
    public void clearData(){
        txt_pengguna.setText("");
        txt_sandi.setText("");
        txt_nama.setText("");
        cbx_tipe.setSelectedItem("");
        txt_status.setText("");
        txt_pembuat.setText("");
        txt_pengguna.requestFocus();
    }
    
    private void form_clear(){
       txt_pengguna.setText("");
       txt_sandi.setText("");
       txt_nama.setText("");
       cbx_tipe.setSelectedItem("");
       txt_status.setText("");
       txt_pembuat.setText("");
    }
    
    public void refreshTable(){
    try{
        Koneksi konek = new Koneksi();
        konek.config();
        Statement stmtTable = konek.con.createStatement();
        String sqlTabel = "select * from operator order by pengguna";
        ResultSet rs = stmtTable.executeQuery(sqlTabel);
        ResultSetMetaData meta = rs.getMetaData();
        String Header[] = {"Pengguna","Sandi","Nama","Tipe","Status","Pembuat"};
        int col = meta.getColumnCount();
        int brs = 0;
        while (rs.next()){
            brs = rs.getRow();
        }
        Object dataTable[][] = new Object[brs][col];
        int x = 0;
        rs.beforeFirst();
        while(rs.next()){
            dataTable[x][0]=rs.getString("pengguna");
            dataTable[x][1]=rs.getString("sandi");
            dataTable[x][2]=rs.getString("nama");
            dataTable[x][3]=rs.getString("tipe");
            dataTable[x][4]=rs.getString("status");
            dataTable[x][5]=rs.getString("pembuat");
            x++;  
        }
        Gridstaff.setModel(new DefaultTableModel(dataTable,Header));
        stmtTable.close();
    }catch(Exception ert) {
        System.out.println(ert.getMessage());
    }
}
    
    private void form_enable(){
        txt_pengguna.setEnabled(true);
        txt_sandi.setEnabled(true);
        txt_nama.setEnabled(true);
        cbx_tipe.setEnabled(true);
        txt_status.setEnabled(true);
        txt_pembuat.setEnabled(true);
    }
    
    private void aksi_tambah(){
        form_enable();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_hapus.setEnabled(false);
        btn_tambah.setEnabled(true);
        
        txt_pengguna.setEnabled(true);
        txt_pengguna.requestFocus(true);
    }
    
     public void deleteData(){
        try{
            Koneksi konek = new Koneksi();
            konek.config();
            String user = txt_pengguna.getText();
            Statement stmt = konek.con.createStatement();
            String sql = "delete from operator where pengguna='" + user + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            JOptionPane.showMessageDialog(null, "Delete User Sukses");
            clearData();
            refreshTable();
            disableData();
        } catch (Exception e){
            System.out.println(e);
    }
}

    public void simpanData(){
        try{
            Koneksi konek = new Koneksi();
            konek.config();
            Statement stmt = konek.con.createStatement();
            Statement stmt1 = konek.con.createStatement();
            String user = txt_pengguna.getText();
            String sql2 = "delete from operator where pengguna='" + user + "'";
            stmt1.executeUpdate(sql2);
            stmt1.close();
            String sql1 = "insert into operator(pengguna, sandi, nama, tipe, status, pembuat)"
                + "values('"
                +txt_pengguna.getText()+"','"+txt_sandi.getText()+"','"+txt_nama.getText()+"','"+cbx_tipe.getSelectedItem()+"','"+txt_status.getText()+"','"+txt_pembuat.getText()+"')";
            stmt.executeUpdate(sql1);
            stmt.close();
            JOptionPane.showMessageDialog(null, "Input/Update User Sukses.");
            clearData();
            refreshTable();
            disableData();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    private void koneksitabel (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql:"
                    + "//localhost:3306/pembayaran", "root", "");
            System.out.println("KONEKSI BERHASIL");
            //JOptionPane.showMessageDialog(null, "");  
        } catch (Exception e) {
            System.out.println("KONEKSI GAGAL \n"+e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_pengguna = new javax.swing.JTextField();
        txt_sandi = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        cbx_tipe = new javax.swing.JComboBox();
        txt_status = new javax.swing.JTextField();
        txt_pembuat = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Gridstaff = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA STAFF");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(243, 243, 243))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel2.setText("Email");

        jLabel3.setText("Sandi");

        jLabel4.setText("Nama");

        jLabel5.setText("Tipe");

        jLabel6.setText("Status");

        jLabel7.setText("Pembuat");

        txt_pengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_penggunaActionPerformed(evt);
            }
        });
        txt_pengguna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_penggunaKeyPressed(evt);
            }
        });

        cbx_tipe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih", "Administrator", "Operator", "Keuangan", " " }));

        Gridstaff.setModel(new javax.swing.table.DefaultTableModel(
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
        Gridstaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GridstaffMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Gridstaff);

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        btn_simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_simpanKeyPressed(evt);
            }
        });

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        btn_hapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_hapusKeyPressed(evt);
            }
        });

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_kembali.setText("Kembali");
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_sandi, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                    .addComponent(txt_pengguna)
                                    .addComponent(cbx_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nama))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_status, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_pembuat))
                                .addGap(20, 20, 20)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_kembali)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_sandi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbx_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_pembuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_simpan)
                    .addComponent(btn_hapus)
                    .addComponent(btn_batal)
                    .addComponent(btn_kembali))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        aksi_tambah();
        txt_pengguna.requestFocus();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        try {
            if (txt_pengguna.getText().equals("")|| txt_sandi.getText().equals("")|| txt_nama.getText().equals("")|| cbx_tipe.getSelectedItem().equals("")|| txt_status.getText().equals("")|| txt_pengguna.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Data Tidak Boleh Kosong","Pesan", JOptionPane.ERROR_MESSAGE);
            }else{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pembayaran","root","");
                st = con.createStatement();
                String simpan = "insert into operator values ('"+txt_pengguna.getText()+"','"
                        + String.valueOf(txt_sandi.getText())+"','"+txt_nama.getText()+"','"+cbx_tipe.getSelectedItem()+"','"+txt_status.getText()+"','"+txt_pembuat.getText()+"')";
                st = con.createStatement();
                int SA = st.executeUpdate(simpan);
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil");
                this.setVisible(false);
                new Login().setVisible(true);
            }
        }catch (Exception e){
            //JOptionPane.showMessageDialog(this, "Akun Sudah Ada","Pesan",JOptionPane.WARNING_MESSAGE);
            simpanData();
        }
        //simpanData();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txt_penggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_penggunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_penggunaActionPerformed

    private void txt_penggunaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_penggunaKeyPressed
        // TODO add your handling code here:
        pengguna = txt_pengguna.getText();
        int tekanenter = evt.getKeyCode();
        if (tekanenter == 10){
            try {
                sql = "Select * from operator "
                + "where pengguna = '"+ pengguna +"'";
                st = con.createStatement();
                RsOperator = st.executeQuery(sql);
                while (RsOperator.next()) {
                    txt_pengguna.setText(RsOperator.getString("pengguna"));
                    txt_sandi.setText(RsOperator.getString("sandi"));
                    txt_nama.setText(RsOperator.getString("nama"));
                    cbx_tipe.setSelectedItem(RsOperator.getString("tipe"));
                    txt_status.setText(RsOperator.getString("status"));
                    txt_pembuat.setText(RsOperator.getString("pembuat"));
                    
                    JOptionPane.showMessageDialog(null,
                        "Data Ditemukan");
                    btn_tambah.setEnabled(false);
                    btn_simpan.setEnabled(false);
                    btn_hapus.setEnabled(true);
                    txt_pengguna.setEnabled(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan \n"+e.getMessage());
                txt_pengguna.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_penggunaKeyPressed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        form_clear();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(true);
        txt_pengguna.requestFocus();
        btn_hapus.setEnabled(true);
        txt_pengguna.setEnabled(true);
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:
        Login lg = new Login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_hapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_hapusKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            deleteData();
        }
    }//GEN-LAST:event_btn_hapusKeyPressed

    private void GridstaffMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridstaffMousePressed
        // TODO add your handling code here:
        JTable table = (JTable)evt.getSource();
        int row = table.getSelectedRow();
        String user = txt_pengguna.getText();
        txt_pengguna.setText((String)table.getValueAt(row, 0));
        txt_sandi.setText((String)table.getValueAt(row, 1));
        txt_nama.setText((String)table.getValueAt(row, 2));
        cbx_tipe.setSelectedItem((String)table.getValueAt(row, 3));
        txt_status.setText((String)table.getValueAt(row, 4));
        txt_pembuat.setText((String)table.getValueAt(row, 5));
        user = txt_pengguna.getText();
        enableData();
    }//GEN-LAST:event_GridstaffMousePressed

    private void btn_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_simpanKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            simpanData();
        }
    }//GEN-LAST:event_btn_simpanKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Operator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Gridstaff;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox cbx_tipe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_pembuat;
    private javax.swing.JTextField txt_pengguna;
    private javax.swing.JTextField txt_sandi;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
