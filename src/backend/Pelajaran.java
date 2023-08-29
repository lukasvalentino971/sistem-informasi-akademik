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
public class Pelajaran {
    private String namaMapel, idMapel;

    public Pelajaran(){}
    public Pelajaran(String idMapel, String namaMapel) {
        this.idMapel = idMapel;
        this.namaMapel = namaMapel;
    }
    
    public ArrayList<Pelajaran> getAll(){
        ArrayList<Pelajaran> listMapel = new ArrayList();
        String sql = "SELECT kode_mapel FROM matapelajaran";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                Pelajaran pelajaran = new Pelajaran();
                pelajaran.setIdMapel(rs.getString("kode_mapel"));
                
                listMapel.add(pelajaran);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listMapel;
    }

    public String getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(String idMapel) {
        this.idMapel = idMapel;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }
    
    
}
