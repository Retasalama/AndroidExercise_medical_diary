/**
 * Created by tanja on 23/02/2018.
 */

public class User {

    int id;
    String username;
    String password;

    //constructor
    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
