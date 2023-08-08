package Server;

public class Person {
    private String fullName;
    private String number;
    private int id;

    public Person(String fullName, String number, int id) {
        this.fullName = fullName;
        this.number = number;
        this.id = id;
    }

    @Override
    public String toString() {
        return fullName+" : "+ number
                ;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
