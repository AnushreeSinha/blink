
package com.colours;


public class Employee {

    private Account BankAcc;
    private Integer Age;
    private String Name;
    private Long id;

    public Account getBankAcc() {
        return BankAcc;
    }

    public void setBankAcc(Account BankAcc) {
        this.BankAcc = BankAcc;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
