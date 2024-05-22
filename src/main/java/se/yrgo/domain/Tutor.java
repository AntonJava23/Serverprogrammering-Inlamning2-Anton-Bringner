package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 25)
    private int id;
    @Column(length = 25)
    private String tutorId;
    @Column(length = 25)
    private String name;
    @Column(length = 25)
    private int salary;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private Set<Student> teachingGroup;

    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    public Tutor() {
    }

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId = tutorId;
        this.name = name;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();

    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);

    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public String getName() {
        return name;
    }


    public Set<Student> getTeachingGroup() {

        Set<Student> unmodifiable =
                Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public Set<Subject> getSubjects() {
        return this.subjectsToTeach;
    }

    public void createStudentAndAddToTeachingGroup(String studentName,
                                                   String enrollmentID, String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }


    public String getTutorId() {
        return tutorId;
    }

    @Override
    public String toString() {
        return name;
    }
}