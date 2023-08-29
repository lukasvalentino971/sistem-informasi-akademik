/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author ar
 */
public class Siswa extends Person {
    private String kelas;
    private Double nilaiRata;
    private int rank;

    public Siswa(){}
    public Siswa(String kelas, String name, String id, String address, String birthDate, char gender) {
        super(name, id, address, birthDate, gender);
        this.kelas = kelas;
    }
    
    public void save(){
        String sql = "INSERT INTO siswa VALUES ('"+getId()+"', '"+getName()+"', '"+getAddress()+"', '"+getGender()+"', "
                + "'"+getBirthDate()+"', '"+getKelas()+"')";
        DBHelper.insertQueryGetId(sql);
        
        sql = "INSERT INTO nilai VALUES('BI', '"+getId()+"', 0)";
        DBHelper.insertQueryGetId(sql);
    }
    
    public void delete(){
        String sql = "DELETE FROM nilai WHERE nisn = '"+getId()+"' ";
        DBHelper.executeQuery(sql);
        
        sql = "DELETE FROM siswa WHERE nisn = "+getId();
        DBHelper.executeQuery(sql);
    }
    
    public void update(){
        String sql = "UPDATE siswa SET nama='"+getName()+"', kelas='"+kelas+"', "
                + "alamat='"+getAddress()+"', jk='"+getGender()+"', tgl_lahir='"+getBirthDate()+"' "
                + "WHERE nisn='"+getId()+"' ";
        DBHelper.executeQuery(sql);
    }
    
    public Siswa getById(String id){
        Siswa siswa = new Siswa();
        String sql = "SELECT * FROM siswa WHERE nisn = '"+id+"' ";
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                siswa = new Siswa();
                siswa.setId(rs.getString("nisn"));
                siswa.setName(rs.getString("nama"));
                siswa.setAddress(rs.getString("alamat"));
                siswa.setGender(rs.getString("jk").charAt(0));
                siswa.setBirthDate(rs.getString("tgl_lahir"));
                siswa.setKelas(rs.getString("kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return siswa;
    }
    
    public int getRankGlobal(){
        int rank = 0;
        String sql = "select rankGlobal from (" +
        "select s.nisn, " +
        "dense_rank() over( order by sum(n.nilai_angka)/(select count(kode_mapel) from matapelajaran) desc ) as rankGlobal " +
        "from siswa s " +
        "join nilai n on(s.nisn = n.nisn) " +
        "group by s.nama " +
        ") ss WHERE nisn='"+getId()+"'";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                rank = rs.getInt("rankGlobal");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rank;
    }
    
    public ArrayList<Siswa> search(String keyword){
//        String sql = "SELECT * FROM siswa WHERE nama LIKE '%"+keyword+"%'";
        String sql = "SELECT s.nisn, s.nama, s.kelas, n.kode_mapel, "
                + "sum(n.nilai_angka)/(select count(kode_mapel) FROM matapelajaran) AS nilairata FROM siswa s "
                + "JOIN nilai n ON(s.nisn = n.nisn) WHERE nama LIKE '%"+keyword+"%' "
                + "GROUP BY s.nama ORDER BY nilairata DESC";
        ResultSet rs = DBHelper.selectQuery(sql);
                
        ArrayList<Siswa> listSiswa = new ArrayList();
        
        try {
            while(rs.next()){
                Siswa siswa = new Siswa();
                siswa.setId(rs.getString("nisn"));
                siswa.setName(rs.getString("nama"));
                siswa.setNilaiRata(rs.getDouble("nilairata"));
                siswa.setRank(siswa.getRankGlobal());
                
                siswa.setKelas(rs.getString("kelas"));
                
                listSiswa.add(siswa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listSiswa;
    }
    
    public ArrayList<Siswa> getAll(){ // get data siswa order by nilai rata-rata
        ArrayList<Siswa> listSiswa = new ArrayList();
        String sql = "SELECT s.nisn, s.nama, s.kelas, n.kode_mapel, "
                + "sum(n.nilai_angka)/(select count(kode_mapel) FROM matapelajaran) AS nilairata, "
                + "dense_rank() over( order by nilairata desc ) AS rankGlobal FROM siswa s " 
                + "JOIN nilai n ON(s.nisn = n.nisn) GROUP BY s.nama ORDER BY nilairata DESC";

        ResultSet rs = DBHelper.selectQuery(sql);
        
        try {
            while(rs.next()){
                Siswa siswa = new Siswa();
                siswa.setId(rs.getString("nisn"));
                siswa.setName(rs.getString("nama"));
                siswa.setKelas(rs.getString("kelas"));
                siswa.setNilaiRata(rs.getDouble("nilairata"));
                siswa.setRank(rs.getInt("rankGlobal"));
                
                listSiswa.add(siswa);
            }
        } catch (Exception e) {
            System.out.println(sql);
            e.printStackTrace();
        }
        
        return listSiswa;
    }
    
    public ArrayList<Siswa> getAllByClass(){ // get data siswa order by kelas and nilai rata-rata
        ArrayList<Siswa> listSiswa = new ArrayList();
        String sql = "SELECT s.nisn, s.nama, s.kelas, " +
            "sum(n.nilai_angka)/(select count(kode_mapel) FROM matapelajaran) AS nilairata, " +
            "dense_rank() over(order by nilairata desc) as rankGlobal " +
            "FROM siswa s JOIN nilai n ON(s.nisn = n.nisn) " +
            "GROUP BY s.nama ORDER BY s.kelas, rankGlobal";
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while(rs.next()){
                Siswa siswa = new Siswa();
                siswa.setId(rs.getString("nisn"));
                siswa.setName(rs.getString("nama"));
                siswa.setKelas(rs.getString("kelas"));
                siswa.setNilaiRata(rs.getDouble("nilairata"));
                siswa.setRank(rs.getInt("rankGlobal"));
                
                listSiswa.add(siswa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listSiswa;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setNilaiRata(Double nilaiRata) {
        this.nilaiRata = nilaiRata;
    }

    public String getKelas() {
        return kelas;
    }

    public Double getNilaiRata() {
        return nilaiRata;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    
}
