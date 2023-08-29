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
public abstract class Person {
    private String name, id, address, birthDate;
    private char gender;

    public Person(){}
    
    public Person(String name, String id, String address, String birthDate, char gender) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
    }
    
    public abstract void update();
    public abstract void delete();
    public abstract void save();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
    
    
    
}
