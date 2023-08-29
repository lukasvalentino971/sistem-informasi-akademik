/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ar
 */
public class Nilai {
    private double nilai = -1;
    private Siswa siswa;
    private Pelajaran mapel;

    public Nilai(){}
    public Nilai(double nilai, Siswa siswa, Pelajaran mapel) {
        this.nilai = nilai;
        this.siswa = siswa;
        this.mapel = mapel;
    }
    
    public void insertNilai() {
        String sql = "INSERT INTO nilai VALUES ('"+mapel.getIdMapel()+"', '"+siswa.getId()+"', '"+this.nilai+"')";
        
        if(isDuplicate()) updateNilai();
        else DBHelper.executeQuery(sql);
        
        this.nilai = nilai;
    }
    
    public boolean isDuplicate(){
        boolean isDuplicate=false;
        String sql = "SELECT COUNT(nilai_angka) AS rowcount FROM nilai WHERE nisn='"+siswa.getId()+"' AND kode_mapel='"+mapel.getIdMapel()+"'";
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            rs.next();
            isDuplicate = rs.getInt("rowcount") > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        }
        
        return isDuplicate;
    }
    
    public void updateNilai(){
        String sql = "UPDATE nilai SET nilai_angka='"+this.nilai+"' WHERE nisn='"+siswa.getId()+"' AND kode_mapel='"+mapel.getIdMapel()+"'";
        DBHelper.executeQuery(sql);
    }
    
    public void setNilai(double nilai){
        this.nilai = nilai;
    }

    public Pelajaran getMapel() {
        return mapel;
    }

    public double getNilai() {
        return nilai;
    }

    public Siswa getSiswa() {
        return siswa;
    }
    
    public ArrayList<Nilai> search(String Keyword){
        ArrayList<Nilai> listNilai = new ArrayList();
        String sql = "SELECT * FROM nilai WHERE nisn LIKE '%"+Keyword+"%' ORDER BY nisn, kode_mapel";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                double nl = rs.getDouble("nilai_angka");
                Siswa sw = new Siswa();
                sw.setId(rs.getString("nisn"));
                
                Pelajaran mpl = new Pelajaran();
                mpl.setIdMapel(rs.getString("kode_mapel"));
                Nilai nilai = new Nilai(nl, sw, mpl);
                
                listNilai.add(nilai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNilai;
    }
}
