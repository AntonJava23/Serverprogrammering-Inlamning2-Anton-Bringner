package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(length = 10)
    private int id;

    @Column(length = 15)
    private String subjectName;
    private int numberOfSemesters;
    private int semesterLength;

    @Column(length = 15)
    @ManyToMany
    private Set<Tutor> tutors;


    public Subject() {}

    public Subject(String subjectName, int numberOfSemesters, int semesterLength) {
        this.subjectName = subjectName;
        this.numberOfSemesters=numberOfSemesters;
        this.semesterLength=semesterLength;
        this.tutors = new HashSet<Tutor>();
    }

    public String subjectName() {
        return subjectName;
    }

    public void addTutorToSubject(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getSubjects().add(this);
    }

    public Set<Tutor> getTutors() {
        return this.tutors;
    }
}

