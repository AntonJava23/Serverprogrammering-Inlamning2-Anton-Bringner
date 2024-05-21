package se.yrgo.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@SecondaryTable(name = "TBL_ADDRESS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 10)
    private int id;
    @Column(unique = true, nullable = false, length = 25)
    private String enrollmentID;
    @Column(length = 15)
    private String name;
    @Column(name = "NUM_COURSES")
    private Integer numberOfCourses;
    @Embedded
    @Column(length = 20)
    private Address address;


    public Student() {
    }

    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }

    public Student(String name, String enrollmentID) {
        this.name = name;
        this.enrollmentID = enrollmentID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public String toString() {
        return name + " lives at: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getEnrollmentID(), student.getEnrollmentID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getEnrollmentID());
    }


}
