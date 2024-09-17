package Auto136;


import java.util.Date;

public class Client extends User {

    public Client(String userID, String fullName, Date dateOfBirth, String address, String phoneNumber, String email, boolean status) {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, UserType.CLIENT, status);
    }

}
