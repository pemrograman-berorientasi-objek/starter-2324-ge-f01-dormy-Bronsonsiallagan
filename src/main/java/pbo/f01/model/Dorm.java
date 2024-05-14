package pbo.f01.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dorms")

public class Dorm {
    @Id
    @Column(name = "name",nullable = false,length = 255 )
    private String name;
    @Column(name = "capacity",nullable = false,length = 255)
    private String capacity;
    @Column(name = "gender",nullable = false,length = 255)
    private String gender;
     
    @ManyToMany(mappedBy = "dorms", cascade = CascadeType.ALL)
    private Set<Student>students;


    public Dorm(String name,String capacity,String gender){
        this.name = name;
        this.capacity = capacity;
        this.gender = gender; 

    }

    public Dorm(String name, String capacity, String gender, Set<Student>students) {
        this.name = name;
        this.capacity = capacity;
        this.gender = gender;
        this.students = students;
    }
    public String getname(){
        return name;
    }
    public String getcapacity(){
        return capacity;
    }
    public String getgender(){
        return gender;
    }
   
    public Set<Student> getStudents(){
        return students;
    }
    public void Students(Set<Student > students) {
        this.students = students;
    }
    @Override 
    public String toString(){
        return name + "|" +  capacity+ "|" + gender;
    }

}
