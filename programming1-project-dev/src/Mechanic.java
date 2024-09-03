import java.util.Date;
public class Mechanic extends User {
    public Mechanic(String fullName, Date dateOfBirth, String address,
                    String phoneNumber, String email, boolean status) {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MECHANIC, status);
    }
}
