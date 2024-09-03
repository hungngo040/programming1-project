import java.util.Date;
public class Manager extends User {
    public Manager(String fullName, Date dateOfBirth, String address,
                   String phoneNumber, String email, boolean status) {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MANAGER, status);
    }
}
