package wipro.utils;

public class test {

	public static void main(String[] args){
		String subject = "hello";
		String message = "test mail";
        String file = "";
        String tomail = "prakash.sonawane@adityabirlacapital.com"; // list of recipient email addresses
        String cc= "sameer.pawar@adityabirlacapital.com";
        String bcc= "";


	MailUtil mail = new MailUtil();
	mail.sendEmailWithAtttachment(tomail, cc, bcc, subject, message, file);
	}

}
