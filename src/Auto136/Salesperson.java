package Auto136;

import java.util.Date;

public class Salesperson extends User {

    public Salesperson(String userID, String fullName, Date dateOfBirth, String address, String phoneNumber, String email, boolean status) {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, UserType.SALESPERSON, status);
    }


}
