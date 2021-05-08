package lk.sanchana_bag_shop.util.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;


@Service
public class TwilioMessageService {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC675bdb247b815e8839997ffed905c9b9";
    public static final String AUTH_TOKEN = "534174caa68acd39afc158c4f7a4c784";


    public void sendSMS(String number, String messageBody) throws Exception{
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber(number), new PhoneNumber("+16193455013"),
                         messageBody)
                .create();

        System.out.println("Message "+message.getSid());
    }
}
