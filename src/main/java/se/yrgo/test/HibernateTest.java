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

//        Query q2 =em.createQuery("select tutor.teachingGroup from Tutor as tutor where tutor.name= 'Johan Smith'");
//        List<Student> studentsForJohan = q2.getResultList();
//        for (Student s : studentsForJohan) {
//            System.out.println(s);
//        }
//
//        Subject programming = em.find(Subject.class, 7);
//        TypedQuery<Tutor> query = em.createQuery("from Tutor tutor where :subject member of tutor.subjectsToTeach", Tutor.class);
//        query.setParameter("subject", programming);
//        List<Tutor> tutorsForProgramming = query.getResultList();
//        for (Tutor tutor : tutorsForProgramming) {
//            System.out.println(tutor);
//        }

//        Query query2 = em.createQuery("from Tutor as tutor join tutor.teachingGroup as student where student.address.city = 'city 2'");
//        List<Object[]> results = query2.getResultList();
//        for (Object[] item : results) {
//            System.out.println(item[0] + " -------------------- " + item[1]);
//        }

//        Query query3 = em.createQuery("select distinct tutor from Tutor as tutor join tutor.teachingGroup as student where student.address.city = 'city 2'");
//        List<Tutor> results2 = query3.getResultList();
//        for (Tutor t : results2) {
//            System.out.println(t);
//        }

        String city = "city 2";
        List<Tutor> results3 = em.createQuery("select distinct tutor from Tutor tutor join tutor.teachingGroup student where student.address.city = :city").setParameter("city", city).getResultList();
        for (Tutor tutor : results3) {
            System.out.println(tutor);
        }


        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
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

