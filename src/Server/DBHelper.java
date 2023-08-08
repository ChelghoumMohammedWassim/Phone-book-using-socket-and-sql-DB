package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public List<Person> getData() throws SQLException {
        ArrayList<Person>list=new ArrayList<Person>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/annuaire", "root", "");
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM annuaire");
        while (res.next()){
            Person person=new Person(res.getString("full_name"),res.getString("number"),res.getInt("id"));
            list.add(person);
        }
        statement.close();
        res.close();
        return list;
    }

    public String getPhoneNumberof(String name) throws SQLException {
        String number="Not found";
        List<Person> list=getData();
        for(Person person:list){
            if (person.getFullName().toUpperCase().equals(name.toUpperCase())){
                number=person.toString();
            }
        }
        return number;
    }

    public List<String> getAll() throws SQLException {
        List<String> numbers=new ArrayList<>();
        String result="";
        List<Person> list=getData();
        for(Person person:list){
            numbers.add(person.toString());
        }
        return numbers;
    }

}
