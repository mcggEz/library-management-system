package com.slms;

public class Member {
    private int id;
    private String name;
    private String school;
    private String gender;
    private String idNumber;

    public Member(int id, String name, String school, String gender, String idNumber) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.gender = gender;
        this.idNumber = idNumber;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSchool() { return school; }
    public String getGender() { return gender; }
    public String getIdNumber() { return idNumber; }
}
