package spring.employees;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Emp {
    private int id;
    private String name;
    private float salary;
    private String designation;
    private String email;

    public Emp() {}

    public static int index = 1;

    public Emp(String name, float salary, String designation, String email) {
        super();
        this.id = index++;
        this.name = name;
        this.salary = salary;
        this.designation = designation;
        this.email = email;
    }

}
