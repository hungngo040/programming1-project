package Auto136;

import java.util.Date;

public class Mechanic extends User {

    public Mechanic(String userID, String fullName, Date dateOfBirth, String address, String phoneNumber, String email, boolean status) {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, UserType.MECHANIC, status);
    }
    
}
