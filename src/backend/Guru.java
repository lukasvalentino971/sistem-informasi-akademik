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
public class Guru extends Person{
//    Pelajaran[] mapel;
    ArrayList<Pelajaran> mapel;

    public Guru() {
        mapel = new ArrayList();
    }

    public Guru(ArrayList<Pelajaran> mapel, String name, String id, String address, String birthDate, char gender) {
        super(name, id, address, birthDate, gender);
        this.mapel = mapel;
    }
    
    public Guru getById(String id){
        Guru guru = new Guru();
        String sql = "SELECT * FROM guru WHERE nip='"+id+"'";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            rs.next();
            guru.setId(rs.getString("nip"));
            guru.setName(rs.getString("nama"));
            guru.setAddress(rs.getString("alamat"));
            guru.setGender(rs.getString("jk").charAt(0));
            guru.setBirthDate(rs.getString("tgl_lahir"));
            guru.getMapelById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guru;
    }
    
    public void getMapelById(String id){
        String sql = "SELECT kode_mapel FROM gurumapel WHERE nip='"+id+"'";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                Pelajaran pelajaran = new Pelajaran();
                pelajaran.setIdMapel(rs.getString("kode_mapel"));
                
                mapel.add(pelajaran);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public ArrayList<Guru> search(String keyword){
        ArrayList<Guru> listGuru = new ArrayList();
        String sql = "SELECT nip, nama FROM guru WHERE nama LIKE '%"+keyword+"%'";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                Guru guru = new Guru();
                guru.setId(rs.getString("nip"));
                guru.setName(rs.getString("nama"));
                
                listGuru.add(guru);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listGuru;
    }
    
    public void insertMapel(String id){
        String sql = "INSERT INTO gurumapel VALUES('"+getId()+"', '"+id+"')";
        DBHelper.insertQueryGetId(sql);
    }
    
    public void delMapel(String kode_mapel){
        String sql = "DELETE FROM gurumapel WHERE nip='"+getId()+"' AND kode_mapel='"+kode_mapel+"'";
        DBHelper.executeQuery(sql);
    }
    
    public void save(){
        String sql = "INSERT INTO guru VALUES('"+getId()+"', '"+getName()+"', '"+getAddress()+"', "
        + "'"+getGender()+"', '"+getBirthDate()+"')";
        DBHelper.insertQueryGetId(sql);
    }
    
    public void update(){
        String sql = "UPDATE guru SET nip='"+getId()+"', nama='"+getName()+"', alamat='"+getAddress()+"', "
                + "jk='"+getGender()+"', tgl_lahir='"+getBirthDate()+"' WHERE nip='"+getId()+"'";
        DBHelper.executeQuery(sql);
    }
    
    public void delete(){
        String sql = "DELETE FROM gurumapel WHERE nip='"+getId()+"'";
        DBHelper.executeQuery(sql);
        
        sql = "DELETE FROM guru WHERE nip='"+getId()+"'";
        DBHelper.executeQuery(sql);
    }

    public ArrayList<Pelajaran> getMapel() {
        return mapel;
    }

    public void setMapel(ArrayList<Pelajaran> mapel) {
        this.mapel = mapel;
    }
    
    
}
