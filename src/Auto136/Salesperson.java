package Auto136;

import java.util.Date;
public class Salesperson extends User {
    public Salesperson(String fullName, Date dateOfBirth, String address,
                       String phoneNumber, String email, boolean status) {
        super(fullName, dateOfBirth, address, phoneNumber, email, User.UserType.SALESPERSON, status);
    }
}

