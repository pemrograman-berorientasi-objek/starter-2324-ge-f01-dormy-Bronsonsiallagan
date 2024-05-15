package pbo.f01;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pbo.f01.model.Student; 
import pbo.f01.model.Dorm; 

public class App { 
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    static final String DELIMITER = "#";

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory("dormy_pu");
        entityManager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String line;
        cleanTables();
        while (true) {
            line = scanner.nextLine();

            if (line.equals("---")) {
                break;
            }
            String[] inputParts = line.split(DELIMITER);
            String command = inputParts[0];
            inputParts = Arrays.copyOfRange(inputParts, 1, inputParts.length);
            switch (command) {
                case "dorm-add":
                    entityManager.getTransaction().begin();
                    Dorm dorm = new Dorm(inputParts[0], inputParts[1], inputParts[2]);
                    entityManager.persist(dorm);
                    entityManager.flush();
                    entityManager.getTransaction().commit();
                    break;

                case "student-add":
                //cek student sudah ada atau belum
                    Student student1 = entityManager.find(Student.class, inputParts[0]);
                    if (student1 != null) {
                        System.out.println("Student already exists");
                        break;
                    }


                    entityManager.getTransaction().begin();
                    Student student = new Student(inputParts[0], inputParts[1], inputParts[2], inputParts[3]);
                    
                    entityManager.persist(student);
                    entityManager.flush();
                    entityManager.getTransaction().commit();
                    break;

                    case "assign":
                        entityManager.getTransaction().begin();
                        Student student2 = entityManager.find(Student.class, inputParts[0]);
                        Dorm dorm1 = entityManager.find(Dorm.class, inputParts[1]);
                    
                        // Check if the dorm and student have compatible genders
                        if (student2 != null && dorm1 != null && student2.getgender().equals(dorm1.getgender()) && dorm1.getSize() < Integer.parseInt(dorm1.getcapacity())){

                            // Add student to dorm and dorm to student only if genders are compatible
                            student2.getDorms().add(dorm1);
                            dorm1.getStudents().add(student2);
                            // Update size of dorm
                            dorm1.setSize(dorm1.getStudents().size());
                        } 
                        entityManager.getTransaction().commit(); 
                    break;
                

                case "display-all":
                    displayAll();
                    break;
            }
        }
        scanner.close();
        entityManager.close();
    }

    private static void displayAll() {
        List<Dorm> dorms = entityManager.createQuery("SELECT d FROM Dorm d", Dorm.class).getResultList();
        dorms.sort(Comparator.comparing(Dorm::getname)); // Sorting dorms by name

        for (Dorm dorm : dorms) {
            System.out.println(dorm);
            List<Student> students = new ArrayList<>(dorm.getStudents());
            students.sort(Comparator.comparing(Student::getname)); // Sorting students by name
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void cleanTables() {
        String deleteStudentJpql = "DELETE FROM Student";
        String deleteDormJpql = "DELETE FROM Dorm";

        entityManager.getTransaction().begin();
        entityManager.createQuery(deleteStudentJpql).executeUpdate();
        entityManager.createQuery(deleteDormJpql).executeUpdate();
        // entityManager.flush();
        entityManager.getTransaction().commit();


        
    }
}
