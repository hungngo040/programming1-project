import java.util.Date;
public class Client extends User {
    private String membershipLevel;  // Silver, Gold, Platinum

    public Client(String fullName, Date dateOfBirth, String address,
                  String phoneNumber, String email, boolean status, String membershipLevel) {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.CLIENT, status);
        this.membershipLevel = membershipLevel;
    }

}
