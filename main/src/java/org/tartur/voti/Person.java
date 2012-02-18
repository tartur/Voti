package org.tartur.voti;

/**
 * A class that models a Person
 * User: user
 * Date: 18/02/12
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class Person {
    protected final String name;
    protected final String lastName;
    protected final String nationalId;

    public Person(String name, String lastName, String nationalId) {
        this.name = name;
        this.lastName = lastName;
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        if (lastName != null ? !lastName.equals(candidate.lastName) : candidate.lastName != null) return false;
        if (name != null ? !name.equals(candidate.name) : candidate.name != null) return false;
        if (nationalId != null ? !nationalId.equals(candidate.nationalId) : candidate.nationalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nationalId != null ? nationalId.hashCode() : 0);
        return result;
    }
}
