package task1;

public class CreateVcf {
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Usage: java CreateVcf <Full Name> <Email> <Phone> <Home Address> <Work Address> <Profile Picture>");
            return;
        }

        String fullName = args[0];
        String email = args[1];
        String phone = args[2];
        String homeAddress = args[3];
        String workAddress = args[4];
        String profilePicture = args[5];

        String vcfContent = generateVcf(fullName, email, phone, homeAddress, workAddress, profilePicture);
        System.out.println(vcfContent);
    }

    private static String generateVcf(String fullName, String email, String phone, String homeAddress, String workAddress, String profilePicture) {
        return "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:" + fullName + "\n" +
                "N:;" + fullName + ";;;\n" +
                "EMAIL;TYPE=INTERNET:" + email + "\n" +
                "TEL;TYPE=CELL:" + phone + "\n" +
                "ADR;TYPE=HOME:;;" + homeAddress + "\n" +
                "ADR;TYPE=WORK:;;" + workAddress + "\n" +
                "PHOTO;VALUE=URI:" + profilePicture + "\n" +
                "END:VCARD\n";
    }
}
