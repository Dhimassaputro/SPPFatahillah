/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppfatahillah;
import java.sql.Connection;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Dhimas
 */
public class Siswa extends javax.swing.JFrame {
    private Connection con;
    private DefaultTableModel tabModel;
    private Statement st;
    private ResultSet RsSiswa;
    private String sql="";
    private String nis, nisn, nama, alamat, status, pengguna;
    /**
     * Creates new form Siswa
     */
    public Siswa() {
        initComponents();
        koneksitabel();
        tampildata("SELECT * FROM siswa");
        System.out.println( LoginInfo.pengguna );
    }

    private void tampildata(String sql){
        DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No");
        datalist.addColumn("NIS");
        datalist.addColumn("NISN");
        datalist.addColumn("Nama Siswa");
        datalist.addColumn("Alamat");
        datalist.addColumn("Status");
        datalist.addColumn("Pengguna");
        
        try {
            int i = 1;
            st = con.createStatement();
            RsSiswa = st.executeQuery("SELECT * FROM siswa");
            while (RsSiswa.next()){
                datalist.addRow(new Object[]{
                    (""+i++),RsSiswa.getString(1), RsSiswa.getString(2), 
                    RsSiswa.getString(3), RsSiswa.getString(5), RsSiswa.getString(4), RsSiswa.getString(6)
                });
                Gridsiswa.setModel(datalist);
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
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
    }
    
    private void form_enable(){
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
    }
    
    public void disableData(){
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
        btn_tambah.setEnabled(true);
        btn_kembali.setEnabled(false);
        btn_simpan.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_batal.setEnabled(false);
        btn_keluar.setEnabled(false);
        btn_tambah.requestFocus();
    }
    
    public void enableData(){
        txt_nis.setEnabled(true);
        txt_nisn.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_alamat.setEnabled(true);
        txt_status.setEnabled(true);
        btn_tambah.setEnabled(false);
        btn_kembali.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_keluar.setEnabled(true);
    }
    
    public void clearData(){
        txt_nis.setText("");
        txt_nisn.setText("");
        txt_nama.setText("");
        txt_alamat.setText("");
        txt_status.setText("");
        txt_nis.requestFocus();
    }
    
    private void form_clear(){
       txt_nis.setText("");
       txt_nisn.setText("");
       txt_nama.setText("");
       txt_alamat.setText("");
       txt_status.setText("");
    }
    
    private void aksi_tambah(){
        form_enable();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(true);
        btn_batal.setEnabled(true);
        btn_hapus.setEnabled(false);
        btn_tambah.setEnabled(true);
        
        txt_nis.setEnabled(true);
        txt_nis.requestFocus(true);
    }
    
    public void deleteData(){
        try{
            Koneksi konek = new Koneksi();
            konek.config();
            String user = txt_nis.getText();
            Statement stmt = konek.con.createStatement();
            String sql = "delete from siswa where nis='" + user + "'";
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
    
    private void koneksitabel (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql:"
                    + "//localhost:3306/pembayaran", "root", "");
            System.out.println("KONEKSI BERHASIL");
            //JOptionPane.showMessageDialog(null, "SELAMAT DATANG");  
        } catch (Exception e) {
            System.out.println("KONEKSI GAGAL \n"+e);
        }
    }
    
    public void refreshTable(){
        try{
            Koneksi konek = new  Koneksi();
            konek.config();
            Statement stmtTable = konek.con.createStatement();
            String sqlTabel = "select * from siswa order by nis";
            ResultSet rs = stmtTable.executeQuery(sqlTabel);
            ResultSetMetaData meta = rs.getMetaData();
            String Header[] = {"NIS","NISN","Nama","Alamat","Status","Pengguna"};
            int col = meta.getColumnCount();
            int brs = 0;
            while (rs.next()){
                brs = rs.getRow();
           }
            Object dataTable[][] = new Object[brs][col];
            int x = 0;
            rs.beforeFirst();
            while(rs.next()){
                dataTable[x][0]=rs.getString("nis");
                dataTable[x][1]=rs.getString("nisn");
                dataTable[x][2]=rs.getString("nama");
                dataTable[x][3]=rs.getString("alamat");
                dataTable[x][4]=rs.getString("status");
                dataTable[x][5]=rs.getString("pengguna");
            
                x++;  
            }
        Gridsiswa.setModel(new DefaultTableModel(dataTable,Header));
        stmtTable.close();
    }catch(Exception ert) {
        System.out.println(ert.getMessage());
    }
}

    public void simpanData(){
        try{
            Koneksi konek = new Koneksi();
            konek.config();
            
            Statement stmt = konek.con.createStatement();
            Statement stmt1 = konek.con.createStatement();
            
            String user = txt_nis.getText();
            String sql2 = "delete from siswa where nis='" + user + "'";
            
            stmt1.executeUpdate(sql2);
            stmt1.close();
            
            String sql1 = "insert into siswa(nis, nisn, nama, alamat, status, pengguna)"
                    + "values('"+txt_nis.getText()+"','"+txt_nisn.getText()+"','"+txt_nama.getText()+"',"
                    + "'"+txt_alamat.getText()+"','"+txt_status.getText()+"',"
                    + "'"+ LoginInfo.getPengguna()+"')";
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
    
    public void pencarian_siswa(){
        String cari = txt_cari.getText();
        Object[] Baris={"NIS","NISN","Nama","Alamat","Status","Pengguna"};
        tabModel = new DefaultTableModel(null, Baris);
        Gridsiswa.setModel(tabModel);
        Connection Koneksi = new Koneksii().getConnection();
        try{
            String sql="Select * from siswa where "
                    + "nis like '%"+cari+"%' "
                    + "OR nisn like '%"+cari+"%' "
                    + "OR nama like '%"+cari+"%' "
                    + "OR alamat like '%"+cari+"%' "
                    + "OR status like '%"+cari+"%' "
                    + "OR pengguna like '%"+cari+"%' "
                    
                    + "order by kode asc";
            java.sql.Statement stmt = Koneksi.createStatement();
            java.sql.ResultSet rslt = stmt.executeQuery(sql);
            while(rslt.next()){
                String nis = rslt.getString("nis");
                String nisn = rslt.getString("nisn");
                String nama = rslt.getString("nama");
                String alamat = rslt.getString("alamat");
                String status = rslt.getString("status");
                String pengguna = rslt.getString("pengguna");
                
                String[] dataField={nis, nisn, nama, alamat, status, pengguna};
                tabModel.addRow(dataField);
            }
        }
        catch(Exception ex){
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
        txt_nis = new javax.swing.JTextField();
        txt_nisn = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_pengguna = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Gridsiswa = new javax.swing.JTable();
        btn_tambah = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 255));
        jPanel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM DATA SISWA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel2.setText("NIS");

        jLabel3.setText("NISN");

        jLabel4.setText("Nama Siswa");

        jLabel5.setText("Alamat");

        jLabel6.setText("Status");

        jLabel7.setText("Pengguna");

        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });

        txt_nisn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisnActionPerformed(evt);
            }
        });

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });

        Gridsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        Gridsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GridsiswaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Gridsiswa);

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

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
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

        btn_kembali.setText("Kembali");
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_cetak.setText("Cetak");

        btn_keluar.setText("Keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        btn_keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_keluarKeyPressed(evt);
            }
        });

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cari))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_batal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_hapus)
                                .addGap(129, 129, 129)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_kembali)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_refresh)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_cetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(btn_keluar, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_kembali)
                            .addComponent(btn_refresh)
                            .addComponent(btn_cetak))
                        .addGap(18, 18, 18)
                        .addComponent(btn_keluar)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_tambah)
                            .addComponent(btn_simpan)
                            .addComponent(btn_batal)
                            .addComponent(btn_hapus))
                        .addGap(0, 46, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisnActionPerformed
        // TODO add your handling code here:
        txt_nama.requestFocus();
    }//GEN-LAST:event_txt_nisnActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        System.out.println( LoginInfo.getPengguna() );
        
        if (LoginInfo.getPengguna() == null){
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }else{
            simpanData();
                //new siswaa().setVisible(true);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        pencarian_siswa();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        pencarian_siswa();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        refreshTable();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_keluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_keluarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }
    }//GEN-LAST:event_btn_keluarKeyPressed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        form_clear();
        btn_tambah.setEnabled(true);
        btn_simpan.setEnabled(true);
        txt_nis.requestFocus();
        btn_hapus.setEnabled(true);
        txt_nis.setEnabled(true);
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_hapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_hapusKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            deleteData();
        }
    }//GEN-LAST:event_btn_hapusKeyPressed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_simpanKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            simpanData();
        }
    }//GEN-LAST:event_btn_simpanKeyPressed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:
        MasterUtama mu = new MasterUtama();

        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        enableData();
        clearData();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void GridsiswaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridsiswaMousePressed
        // TODO add your handling code here:
        JTable table = (JTable)evt.getSource();
        int row = table.getSelectedRow();
        String user = txt_nis.getText();
        txt_nis.setText((String)table.getValueAt(row, 0));
        txt_nisn.setText((String)table.getValueAt(row, 1));
        txt_nama.setText((String)table.getValueAt(row, 2));
        txt_alamat.setText((String)table.getValueAt(row, 3));
        txt_status.setText((String)table.getValueAt(row, 4));
        user = txt_nis.getText();
        enableData();
    }//GEN-LAST:event_GridsiswaMousePressed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
        txt_status.requestFocus();
    }//GEN-LAST:event_txt_alamatActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
        txt_alamat.requestFocus();
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
        txt_nisn.requestFocus();
    }//GEN-LAST:event_txt_nisActionPerformed

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
            java.util.logging.Logger.getLogger(Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Siswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Gridsiswa;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nisn;
    private javax.swing.JTextField txt_pengguna;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
