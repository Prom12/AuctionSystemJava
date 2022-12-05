package shared;

public class UserDetails{
    public String name = "";
    String email = "";
    public String phoneNo = "";
    public String userType = "";
    public String userId = "";

    public UserDetails(String name,String email,String phoneNo,String userType,String userId){
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.userType = userType;
        this.userId = userId;
        
    }
}