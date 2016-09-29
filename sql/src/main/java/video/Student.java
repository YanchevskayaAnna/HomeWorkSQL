package video;

import java.util.Date;

/**
 * Created by sf on 28.09.16.
 */
public class Student {

    private String name;
    private String mail;
    private double salary;
    private Date date;

    public Student(String name, String mail, double salary, Date date) {
        this.name = name;
        this.mail = mail;
        this.salary = salary;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "video.Student{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", salary=" + salary +
                ", date=" + date +
                '}';
    }
}
