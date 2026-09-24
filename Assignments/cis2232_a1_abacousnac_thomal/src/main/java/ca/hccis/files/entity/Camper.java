package ca.hccis.files.entity;

import java.util.Scanner;

public class Camper {

    private int id;
    private int registrationId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    public Camper() {
    }

    public Camper(int id, int registrationId, String firstName, String lastName, String dateOfBirth) {
        this.id = id;
        this.registrationId = registrationId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public void getInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Registration ID: ");
        registrationId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Date of Birth: ");
        dateOfBirth = scanner.nextLine();
    }

    public void edit(){
        String fName = ca.hccis.util.CisUtility.getInputString("First Name: ");
        String lName = ca.hccis.util.CisUtility.getInputString("Last Name: ");
        String dob = ca.hccis.util.CisUtility.getInputString("DOB: ");

        setFirstName(fName);
        setLastName(lName);
        setDateOfBirth(dob);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format(
                "Camper: registrationId=%d, firstName='%s', lastName='%s', dateOfBirth='%s'",
                registrationId, firstName, lastName, dateOfBirth
        );
    }

}
