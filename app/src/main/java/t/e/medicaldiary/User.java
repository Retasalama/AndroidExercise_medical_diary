package t.e.medicaldiary;


/**
 * Created by tanja on 23/02/2018.
 */

public class User {

    private static String id;
    private static String username;
    private static String password;

    //constructor
    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
