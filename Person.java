public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     *
     * @return returns the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return returns the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if(!(obj instanceof Person))return false;
        Person person=(Person) obj;
        return getFirstName().equals(person.getFirstName())&&
                getLastName().equals(person.getLastName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
