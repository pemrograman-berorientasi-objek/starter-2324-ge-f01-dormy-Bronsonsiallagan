package pbo.f01;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import pbo.f01.model.Student;
import pbo.f01.model.Dorm;

/**
 * 12S22025 - Bronson Siallagan
 * 12S22026 - Ruben Sianipar
 */
public class App {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    static final String pisah = "#";
    public static void main(String[] _args) {
        factory = Persistence.createEntityManagerFactory( "dormy_pu");
        entityManager = factory.createEntityManager();

        Scanner scanner = new Scanner (System.in );
        String line = null; 
        cleanTables(); 
        while (true) {
            line = scanner.nextLine();

            if (line.equals ("---")) { 
                break;
            }
            String[] inputParts = line.split(pisah);
            String command = inputParts[0];
            inputParts = Arrays.copyOfRange(inputParts,1,inputParts.length);
                switch (command){
                    case "dorm-add":
                    System.out.println("dorm-add");
                    entityManager.getTransaction().begin();
                    Dorm tempt = new Dorm(inputParts[0],inputParts[1], inputParts[2]);
                    entityManager.persist(tempt);
                    entityManager.flush();
                    entityManager.getTransaction().commit(); 


                    case "student-add":
                    System.out.println("student-add"); 
                    entityManager.getTransaction().begin();
                    Student tempt1 =  new Student(inputParts[0],inputParts[1],inputParts[2],inputParts[3]);
                    entityManager.persist(tempt1);
                    entityManager.flush();
                    entityManager.getTransaction().commit(); 
                    case "assign":

                    case  "display-all":
                    displayAllStudents();
                    displayAllDorms();
                    }
        }
        entityManager.close(); 
    }
    private static void displayAllStudents(){
     String jpql ="PILIH c DARI  Student c diambil dari c.name";
     List<Student>students = entityManager.createQuery(jpql,Student.class).getResultList();
     System.out.println("displayAllStudents");
     for(Student c:students){
        System.out.println(c);
     }
    }
    private static void displayAllDorms(){
        String jpql ="PILIH c DARI  Dorm c diambil dari c.name";
        List<Dorm>dorms = entityManager.createQuery(jpql,Dorm.class).getResultList();
        System.out.println("displayAllDorms");
        for(Dorm c:dorms){
           System.out.println(c);
        }
       }
       private static void cleanTables(){
        String deleteStudentJpql = "Delete From Student c";
        String deleteDormJpql ="Delete From Dorm c";

        entityManager.getTransaction().begin();
        int deleteStudents = entityManager.createQuery(deleteStudentJpql).executeUpdate();
        int deleteDorms = entityManager.createQuery(deleteDormJpql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        
        System.out.println("cleanTables"+ deleteStudents +"Students"); 
        System.out.println("cleanTables"+ deleteDorms +"Dorms");
       }
}