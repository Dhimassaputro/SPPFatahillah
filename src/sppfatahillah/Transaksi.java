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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Dhimas
 */
public class Transaksi extends javax.swing.JFrame {

    private Connection con;
    private DefaultTableModel tabmode;
    private Statement st;
    private ResultSet RsSiswa;
    private ResultSet RsTransaksi;
    private ResultSet RsSpp;
    private String sql = "";
    
    private int jumlahBayar = 0 ;
    
    private String no, nis, nisn, nama, kelas, pengguna;
    private int biaya, kembali, bulan, tahun, jumlahtotal;

    private void tampildata(String sql){
        DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No");
        datalist.addColumn("NIS");
        datalist.addColumn("NISN");
        datalist.addColumn("Nama");
        datalist.addColumn("Kelas");
        datalist.addColumn("Biaya");
        datalist.addColumn("Pengguna");
        datalist.addColumn("Kembali");
        datalist.addColumn("Bulan");
        datalist.addColumn("Tahun");
        try {
            int i = 0;
            st=con.createStatement();
            RsTransaksi = st.executeQuery("SELECT * FROM transaksi");
            while (RsTransaksi.next()){
                datalist.addRow(new Object[]{
                    (""+i++), 
                    RsTransaksi.getString(1), RsTransaksi.getString(2), RsTransaksi.getString(3), 
                    RsTransaksi.getString(4), RsTransaksi.getString(5), RsTransaksi.getString(6), 
                    RsTransaksi.getString(7), RsTransaksi.getString(8), RsTransaksi.getString(9)
                });
                Gridtransaksi.setModel(datalist);
        }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        }
    }
    
    private void form_awal(){
        form_disable();
        form_clear(); 
        Btn_Simpan.setText("Simpan");
        btn_tambah.requestFocus(true);
        btn_tambah.setEnabled(true);
        Btn_Simpan.setEnabled(false);
        Btn_Batal.setEnabled(false);
    }
    
    private void form_disable(){
        txt_no.setEnabled(false);
        txt_tanggal.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_biaya.setEnabled(false);
        txt_jumlah_transaksi.setEnabled(false);
        txt_total.setEnabled(false);
        txt_jumlahbayar.setEnabled(false);
        txt_kembali.setEnabled(false);
        
        Cmb_kl.setEnabled(false);
    }
    
    public void disableData(){
        txt_no.setEnabled(false);
        txt_tanggal.setEnabled(false);
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_biaya.setEnabled(false);
        txt_jumlah_transaksi.setEnabled(false);
        txt_total.setEnabled(false);
        txt_jumlahbayar.setEnabled(false);
        txt_kembali.setEnabled(false);
        Cmb_kl.setEnabled(false);
        btn_tambah.setEnabled(true);
        Btn_Kembali.setEnabled(false);
        Btn_Simpan.setEnabled(false);
        Btn_Batal.setEnabled(false);
        btn_tambah.requestFocus();
    }
    
    public void enableData(){
        txt_no.setEnabled(true);
        txt_tanggal.setEnabled(true);
        txt_nis.setEnabled(true);
        txt_nisn.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_biaya.setEnabled(true);
        txt_jumlah_transaksi.setEnabled(true);
        txt_total.setEnabled(true);
        txt_jumlahbayar.setEnabled(true);
        txt_kembali.setEnabled(true);
        Cmb_kl.setEnabled(true);
        btn_tambah.setEnabled(false);
        Btn_Kembali.setEnabled(true);
        Btn_Simpan.setEnabled(true);
        Btn_Batal.setEnabled(true);
    }
    
    public void clearData(){
        txt_no.setText(null);
        txt_tanggal.setDate(null);
        txt_nis.setText(null);
        txt_nisn.setText(null);
        txt_nama.setText(null);
        txt_biaya.setText(null);
        
        txt_jumlahbayar.setText("");
        txt_jumlah_transaksi.setText("");
        txt_total.setText("");
        txt_kembali.setText("");
    }
    
    private void form_clear(){
        txt_no.setText("");
        txt_nis.setText("");
        txt_nisn.setText("");
        txt_nama.setText("");
        txt_biaya.setText("");
        
        txt_jumlahbayar.setText("");
        txt_jumlah_transaksi.setText("");
        txt_total.setText("");
        txt_kembali.setText("");

        Cmb_kl.setSelectedItem("Pilih");
    }
    
    public void refreshTable(){
    try{
        Koneksi konek = new Koneksi();
        konek.config();
        Statement stmtTable = konek.con.createStatement();
        String sqlTabel = "select * from transaksi order by nis";
        ResultSet rs = stmtTable.executeQuery(sqlTabel);
        ResultSetMetaData meta = rs.getMetaData();
        String Header[] = {"NIS","NISN","Nama","Kelas","Biaya","Pengguna","Kembali","Bulan","Tahun"};
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
            dataTable[x][3]=rs.getString("kode_kelas");
            dataTable[x][4]=rs.getString("biaya");
            dataTable[x][5]=rs.getString("pengguna");
            dataTable[x][6]=rs.getString("kembali");
            dataTable[x][7]=rs.getString("bulan");
            dataTable[x][8]=rs.getString("tahun");
            x++;  
        }
        Gridtransaksi.setModel(new DefaultTableModel(dataTable,Header));
        stmtTable.close();
    }catch(Exception ert) {
        System.out.println(ert.getMessage());
    }
}
 
    private void form_enable(){
        txt_no.setEnabled(true);
        txt_tanggal.setEnabled(true);
        txt_nis.setEnabled(true);
        txt_nisn.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_biaya.setEnabled(true);
        txt_jumlah_transaksi.setEnabled(true);
        txt_total.setEnabled(true);
        txt_jumlahbayar.setEnabled(true);
        txt_kembali.setEnabled(true);
        Cmb_kl.setEnabled(true);
    }
    
    private void aksi_tambah(){
        form_enable();
        Btn_Tambah.setEnabled(true);
        Btn_Kurang.setEnabled(true);
        Btn_Simpan.setEnabled(true);
        Btn_Batal.setEnabled(true);
        txt_no.requestFocus(true);
        txt_no.setEnabled(true);
    }
    
    public void simpanData(){
        try{
            Koneksi konek = new Koneksi();
            konek.config();
            Statement stmt = konek.con.createStatement();
            Statement stmt1 = konek.con.createStatement();
            String user = txt_nis.getText();
            String sql2 = "delete from transaksi where nis='" + user + "'";
            stmt1.executeUpdate(sql2);
            stmt1.close();
            String sql1 = "insert into transaksi(nis, nisn, nama, kode_kelas, biaya, pengguna, kembali, bulan, tahun)"
                + "values('"
                    //+txt_nis.getText()+"','"+txt_nisn.getText()+"','"+txt_nama.getText()+"','"+txt_biaya.getText()+"','"+txt_kembali.getText()+",)";
                +txt_nis.getText()+"','"+txt_nisn.getText()+"','"+txt_nama.getText()+"','"+Cmb_kl.getSelectedItem()+"','"+txt_biaya.getText()+"','"+txt_kembali.getText()+"','"+cbx_bulan.getSelectedItem()+"')";
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
        } catch (Exception e) {
            System.out.println("KONEKSI GAGAL \n"+e);
        }
    }
    
    public Transaksi() {
        try {
            initComponents();
            
            this.setVisible(true);
            txt_no.setText( MyConstanta.NO_TRANSAKSI );
            
            Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse( MyConstanta.TANGGAL_TRANSAKSI );
            
            txt_tanggal.setDate( tempDate );
            txt_nis.setText( MyConstanta.NIS );
            txt_nisn.setText( MyConstanta.NISN );
            txt_nama.setText( MyConstanta.NAMA );
            koneksitabel();
            //DaftarSiswa();
            DaftarSpp();
            //tampildata("SELECT * FROM transaksi");
            //tampildetailtransaksi("select * from detailtransaksi");
        } catch (ParseException ex) {
            Logger.getLogger(Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void DaftarSiswa(){
        try {
            String sql = "Select * FROM siswa";
            Statement st = con.createStatement();
            RsSiswa = st.executeQuery(sql);
            while(RsSiswa.next()){
                String Alliasob=RsSiswa.getString("NIS");
                txt_nis.setText(Alliasob);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan \n"
                +e.getMessage());
        }
    }
    
    private void DaftarSpp(){
        Cmb_kl.removeAllItems();
        Cmb_kl.addItem("Pilih");
        
        try {
            String sql = "Select kelas, biaya FROM spp";
            
            //Connection con = new koneksii().getConnection();
            Statement st = con.createStatement();
            RsSpp = st.executeQuery(sql);
            
            while(RsSpp.next()){
                String Alliasps = RsSpp.getString("kelas");
                Cmb_kl.addItem(Alliasps);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Kode SPP \n"
                +e.getMessage());
        }
    }
    
    private void prosestambah(){
    try {
        DefaultTableModel tableModel = (DefaultTableModel)Gridspp.getModel();
        String[]data = new String[6];
        data[0] = String.valueOf(Cmb_kl.getSelectedItem());
        data[1] = txt_biaya.getText();
        data[3] = txt_total.getText();
        tableModel.addRow(data);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error Memasukkan Data \n" +e.getMessage());
    }
}
    private void total(){
        int jumlahBaris = Gridspp.getRowCount();
        int jumlahtotal = 0, jumlahitem = 0;
        int jumlahbiaya, totalharga;
        
        TableModel tblmodel;
        tblmodel = Gridspp.getModel();
        for (int i=0; i<jumlahBaris; i++){
            jumlahbiaya = Integer.parseInt(tblmodel.getValueAt(i, 2).toString());
            jumlahitem = jumlahitem+jumlahbiaya;
            totalharga = Integer.parseInt(tblmodel.getValueAt(i, 3).toString());
            jumlahtotal=jumlahtotal+totalharga;}
        txt_jumlah_transaksi.setText(String.valueOf(jumlahitem));
        txt_total.setText(String.valueOf(jumlahtotal));
    }

    private void simpandetail(){
            int jumlah_baris = Gridspp.getRowCount();
            if(jumlah_baris == 0){
                JOptionPane.showMessageDialog(rootPane, "Tabel Masih Kosong!");
            }else{
                try {
                    int i = 1 ;
                    while(i < jumlah_baris){
                        st.executeUpdate("insert into v_transaksi_pembayaran"
                        + "(nis,kode_kelas,pengguna,bulan,tahun,biaya,dibuat) "
                        + "values('"+txt_no.getText() +"',"
                        + "'"+Gridspp.getValueAt(i, 0)+"',"
                        + "'"+Gridspp.getValueAt(i, 1)+"',"
                        + "'"+Gridspp.getValueAt(i, 2)+"',"
                        + ","+Gridspp.getValueAt(i, 3)+"',"
                        + ","+Gridspp.getValueAt(i, 4)+"',"
                        + ","+Gridspp.getValueAt(i, 5)+"',"
                        + ","+Gridspp.getValueAt(i, 6)+"',");
                    try {
                        sql="SELECT * FROM transaksi WHERE "
                                + "nis='" + Gridspp.getValueAt(i, 0) +"'";
                        st=con.createStatement();
                        RsSpp=st.executeQuery(sql);
                        while(RsSpp.next()){
                            try {
                               
                            st=con.createStatement();
                            st.execute(sql);
                            
                            } catch (Exception err) {
                                JOptionPane.showConfirmDialog(null, "Tidak Ada Barang Update!\n"+err.getMessage());
                                
                            }
                        }
                        } catch (Exception se) {
                                JOptionPane.showConfirmDialog(null, "Data Tidak Ditemukan!!\n"+se.getMessage());
                                txt_nis.requestFocus();
                                }
                    i++;
                        
                    } //JOptionPane.showMessageDialog(rootPane, "Berhasil Disimpan!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan ! ERROR : \n"+e);
                }
            }
        }

    /**
     * Creates new form Transaksi
     */
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        txt_nisn = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_biaya = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Cmb_kl = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txt_jumlah_transaksi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_jumlahbayar = new javax.swing.JTextField();
        txt_kembali = new javax.swing.JTextField();
        Btn_Tambah = new javax.swing.JButton();
        Btn_Kurang = new javax.swing.JButton();
        Btn_Kembali = new javax.swing.JButton();
        Btn_Simpan = new javax.swing.JButton();
        Btn_Batal = new javax.swing.JButton();
        Btn_Keluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Gridtransaksi = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_no = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Gridspp = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        txt_nis = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbx_bulan = new javax.swing.JComboBox();
        txt_tanggal = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel13.setText("NIS");

        jLabel9.setText("NISN");

        jLabel12.setText("Nama");

        jLabel10.setText("Kelas");

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel2.setText("Bulan");

        jLabel3.setText("Total");

        Cmb_kl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih" }));
        Cmb_kl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Cmb_klItemStateChanged(evt);
            }
        });

        jLabel4.setText("Jumlah / Bulan");

        txt_jumlah_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah_transaksiActionPerformed(evt);
            }
        });

        jLabel6.setText("Jumlah Bayar");

        jLabel7.setText("Kembali");

        txt_jumlahbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahbayarActionPerformed(evt);
            }
        });

        Btn_Tambah.setText("+");
        Btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TambahActionPerformed(evt);
            }
        });

        Btn_Kurang.setText("-");
        Btn_Kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KurangActionPerformed(evt);
            }
        });

        Btn_Kembali.setText("Kembali");
        Btn_Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KembaliActionPerformed(evt);
            }
        });

        Btn_Simpan.setText("Simpan");
        Btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanActionPerformed(evt);
            }
        });
        Btn_Simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_SimpanKeyPressed(evt);
            }
        });

        Btn_Batal.setText("Batal");
        Btn_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BatalActionPerformed(evt);
            }
        });

        Btn_Keluar.setText("Keluar");
        Btn_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KeluarActionPerformed(evt);
            }
        });
        Btn_Keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_KeluarKeyPressed(evt);
            }
        });

        Gridtransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        Gridtransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GridtransaksiMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(Gridtransaksi);

        jLabel15.setText("No Transaksi");

        jLabel16.setText("Tanggal Transaksi");

        Gridspp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bulan", "Biaya"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Gridspp);

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btn_cari.setText("Cari");
        btn_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cariMouseClicked(evt);
            }
        });

        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cari...");
        jButton2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jButton2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbx_bulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Btn_Kembali)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Btn_Simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Btn_Batal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Btn_Keluar)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(btn_cari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(45, 45, 45))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addGap(42, 42, 42)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel2))
                                .addGap(43, 43, 43)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2))
                                .addComponent(txt_nisn)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(Cmb_kl, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_biaya, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                .addComponent(txt_nama)
                                .addComponent(cbx_bulan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Btn_Tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Btn_Kurang)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_jumlah_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_total, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(txt_jumlahbayar)
                                    .addComponent(txt_kembali)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_no, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txt_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txt_jumlah_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cmb_kl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txt_biaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbx_bulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Btn_Tambah)
                            .addComponent(Btn_Kurang))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_jumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Kembali)
                    .addComponent(Btn_Simpan)
                    .addComponent(Btn_Batal)
                    .addComponent(Btn_Keluar)
                    .addComponent(jButton1)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void Cmb_klItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Cmb_klItemStateChanged
        String k = (String)Cmb_kl.getSelectedItem();

        if ( k == null || k.equals("Pilih") ) return;

        try {
            sql = "Select * FROM spp WHERE "
            + "kelas = '"+ k +"'";

            st = con.createStatement();
            RsSpp = st.executeQuery(sql);

            while(RsSpp.next()){
                txt_biaya.setText(RsSpp.getString("biaya"));
            }
        } catch (Exception e) {
            System.out.println( "Gagal Menampilkan Biaya SPP \n"
                +e.getMessage() );
        }
    }//GEN-LAST:event_Cmb_klItemStateChanged

    private void txt_jumlah_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah_transaksiActionPerformed
        // TODO addsdfasfd your handling code here:
    }//GEN-LAST:event_txt_jumlah_transaksiActionPerformed

    private void txt_jumlahbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahbayarActionPerformed
        // TODO add your handling code here:
        jumlahtotal = Integer.parseInt(txt_total.getText());
        biaya = Integer.parseInt(txt_jumlahbayar.getText());
        kembali = biaya-jumlahtotal;
        txt_kembali.setText(String.valueOf(kembali));
    }//GEN-LAST:event_txt_jumlahbayarActionPerformed

    private void Btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TambahActionPerformed
        // TODO add your handling code here:
        //prosestambah();
        //total();

        String bulan = cbx_bulan.getSelectedItem().toString();

        Object[] row = { cbx_bulan.getSelectedItem().toString(),
            txt_biaya.getText().toString() };

        DefaultTableModel model = (DefaultTableModel) Gridspp.getModel();

        model.addRow(row);

        jumlahBayar += Integer.valueOf( txt_biaya.getText() );

        txt_total.setText(String.valueOf(jumlahBayar));

        txt_jumlah_transaksi.setText( String.valueOf( model.getRowCount() ) );
    }//GEN-LAST:event_Btn_TambahActionPerformed

    private void Btn_KurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KurangActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) Gridspp.getModel();
        int row = Gridspp.getSelectedRow();

        if( row >= 0 ){
            int oke = JOptionPane.showConfirmDialog(null,
                "Yakin Mau Hapus?","Konfirmasi",
                JOptionPane.YES_NO_OPTION);

            System.out.println( oke );

            if( oke == 0 ){
                jumlahBayar -= Integer.valueOf( Gridspp.getModel().getValueAt(row, 1).toString() );
                txt_total.setText(String.valueOf(jumlahBayar));
                model.removeRow(row);
            }
        }

        txt_jumlah_transaksi.setText( String.valueOf( model.getRowCount() ) );
    }//GEN-LAST:event_Btn_KurangActionPerformed

    private void Btn_KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KembaliActionPerformed
        // TODO add your handling code here:
        MasterUtama mu = new MasterUtama();

        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Btn_KembaliActionPerformed

    private void Btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanActionPerformed
        // TODO add your handling code here: 
        String Tanggal , NIS;
        int totalItem, total, biaya, kembali, totalBayar;

        nis = String.valueOf(txt_nis.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Tanggal = format.format(txt_tanggal.getDate());
        kelas = Cmb_kl.getItemAt(Cmb_kl.getSelectedIndex()).toString();

        totalItem = Integer.parseInt(txt_jumlah_transaksi.getText());
        biaya = Integer.parseInt(txt_jumlahbayar.getText());
        kembali = Integer.parseInt(txt_kembali.getText());
        totalBayar = Integer.parseInt(txt_total.getText());
        simpandetail();
        try {
            sql="INSERT INTO transaksi(nis, "
            + "nisn, "
            + "nama, "
            + "kode_kelas, "
            + "biaya, "
            + "pengguna, "
            + "kembali, "
            + "bulan, "
            + "tahun)VALUES"
            + "('"+ nis +"',"
            + "'"+ nisn +"',"
            + "'"+ nama +"',"
            + "'"+ kelas +"',"
            + "'"+ biaya +"',"
            + "'"+ pengguna +"',"
            + "'"+ kembali +"',"
            + "'"+ bulan +"',"
            + "'"+ tahun + "')";
            st = con.createStatement();
            st.execute(sql);
            tampildata("Select * from transaksi");
            form_awal();
            JOptionPane.showMessageDialog(null,
                "Data Tersimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan \n"+e.getMessage());
        }
        //simpanData();
    }//GEN-LAST:event_Btn_SimpanActionPerformed

    private void Btn_SimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_SimpanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_SimpanKeyPressed

    private void Btn_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BatalActionPerformed
        // TODO add your handling code here:
        form_clear();
        form_enable();
        Btn_Tambah.setEnabled(true);
        Btn_Simpan.setEnabled(true);
        Btn_Kurang.setEnabled(true);
        txt_no.requestFocus();
        txt_no.setEnabled(true);
    }//GEN-LAST:event_Btn_BatalActionPerformed

    private void Btn_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_Btn_KeluarActionPerformed

    private void Btn_KeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_KeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_KeluarKeyPressed

    private void GridtransaksiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridtransaksiMousePressed
        // TODO add your handling code here:
        JTable table = (JTable)evt.getSource();
        int row = table.getSelectedRow();
        String user = txt_nis.getText();
        txt_nis.setText((String)table.getValueAt(row, 0));
        txt_nisn.setText((String)table.getValueAt(row, 1));
        txt_nama.setText((String)table.getValueAt(row, 2));
        txt_biaya.setText((String)table.getValueAt(row, 3));
        txt_jumlahbayar.setText((String)table.getValueAt(row, 4));
        txt_jumlah_transaksi.setText((String)table.getValueAt(row, 5));
        txt_total.setText((String)table.getValueAt(row, 6));
        txt_kembali.setText((String)table.getValueAt(row, 7));
        user = txt_nis.getText();
        enableData();
    }//GEN-LAST:event_GridtransaksiMousePressed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        //pencarian_transaksi();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cariMouseClicked
        // TODO add your handling code here:
        //pencarian_transaksi();
    }//GEN-LAST:event_btn_cariMouseClicked

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
        try {
            sql="Select * FROM siswa WHERE "
            + "NIS='" + txt_nis.getText() +"'";
            st=con.createStatement();
            RsSiswa=st.executeQuery(sql);
            while(RsSiswa.next()){
                txt_nisn.setText(RsSiswa.getString("nisn"));
                txt_nama.setText(RsSiswa.getString("nama"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Detail Pelanggan \n"
                +e.getMessage());
        }
    }//GEN-LAST:event_txt_nisActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        aksi_tambah();
        txt_no.requestFocus();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        /*try {
            koneksi konek = new koneksi();
            konek.config();
            Map prs = new HashMap();
            JasperReport Jrpt =
            JasperCompileManager.compileReport("E:\\Documents\\NetBeansProjects\\spp_fatahillah\\src\\spp_fatahillah\\reporttransaksi.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(Jrpt, prs,konek.con);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e) {
        }*/
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jButton2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2AncestorAdded

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        MyConstanta.NO_TRANSAKSI = txt_no.getText();

        //set tanggal
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        MyConstanta.TANGGAL_TRANSAKSI = formater.format( txt_tanggal.getDate() );

        //tutup form ini
        this.setVisible( false );

        DataSiswa ds = new DataSiswa();
        ds.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Batal;
    private javax.swing.JButton Btn_Keluar;
    private javax.swing.JButton Btn_Kembali;
    private javax.swing.JButton Btn_Kurang;
    private javax.swing.JButton Btn_Simpan;
    private javax.swing.JButton Btn_Tambah;
    private javax.swing.JComboBox Cmb_kl;
    private javax.swing.JTable Gridspp;
    private javax.swing.JTable Gridtransaksi;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox cbx_bulan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txt_biaya;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_jumlah_transaksi;
    private javax.swing.JTextField txt_jumlahbayar;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nisn;
    private javax.swing.JTextField txt_no;
    private com.toedter.calendar.JDateChooser txt_tanggal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
