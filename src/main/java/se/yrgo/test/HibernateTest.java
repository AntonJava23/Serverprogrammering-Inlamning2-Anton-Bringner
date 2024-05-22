package se.yrgo.test;

import jakarta.persistence.*;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Uppgift 1 - Navigera över relationer(med member of)
        Subject science = em.find(Subject.class, 2);
        Query query1 = em.createQuery("select t.teachingGroup from Tutor t where :subject member of t.subjectsToTeach");
        query1.setParameter("subject", science);
        List<Student> withScienceTutors = query1.getResultList();
        System.out.println("All students with a tutor that can teach science:");
        for (Student student : withScienceTutors) {
            System.out.println("- " + student);
        }

        // Uppgift 2 - Report Query Multiple fields (med join)
        List<Object[]>query2 = em.createQuery(
                "from Tutor as tutor join tutor.teachingGroup as student").getResultList();
        for(Object[] obj:query2) {
            System.out.println("- Tutor: " + obj[0] + " || Student: " + obj[1]);
        }

        // Uppgift 3 - Report Query Aggregation
        double averageSemesterLength = (Double)em.createQuery("select avg(subject.semesterLength) from Subject subject")
                .getSingleResult();
        System.out.println("Average semester length: " + averageSemesterLength);

        // Uppgift 4 - Query med Aggregation
        int highestSalary = (int)em.createQuery("select max(tutor.salary) from Tutor tutor")
                .getSingleResult();
        System.out.println("Tutor with highest salary: " + highestSalary);


        // Uppgift 5 - NamedQuery
        // Skriv en named query som kan returnera alla tutors som har en lön högre än 10 000.
        List<Tutor> query5 = em.createNamedQuery("searchBySalary", Tutor.class).getResultList();
        System.out.println("Tutors with a salary higher than 10000");
        for(Tutor tutor: query5) {
            System.out.println("- " + tutor);
        }


        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2, 30);
        Subject science = new Subject("Science", 2, 25);
        Subject programming = new Subject("Programming", 3, 44);
//        em.persist(mathematics);
//        em.persist(science);
//        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);


        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);

        // This tutor is the only tutor who can teach History
        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

//        em.persist(t1);
//        em.persist(t2);
//        em.persist(t3);


        t1.createStudentAndAddToTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
        t1.createStudentAndAddToTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddToTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

        tx.commit();
        em.close();
    }

}

