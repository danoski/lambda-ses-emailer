package com.rave.emailer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Useradmin on 22/06/2017.
 */
public class EmailTest {
    public String start(ScheduledEvent scheduledEvent, Context context) {
        int task = 2;
        switch (task) {
            case 1:
                sendMailForPasswordReset();
                break;
            case 2:
                sendMailForVehicleRegistration();
                break;
            case 3:
                sendMailForUserRegistration();
                break;
            default:
                sendMailForPasswordReset();
        }
        return "done";
    }

    private void sendMailForPasswordReset(){
        String from = "dan_nwankwo@yahoo.co.uk";

        String messageBody = MessageBody.getPasswordResetMessage(StaticResources.PASSWORD_RESET_FILENAME);
        String subject = "Email sending test";
        Message message = buildMessageObjectForMail(subject, messageBody);
        getEmailToList().forEach(to -> sendMail(from,to,message));
    }

    private void sendMailForVehicleRegistration(){
        String from = "admin@nigeriachristian.com";

        String messageBody = MessageBody.getVehicleRegistrationMessage(StaticResources.VEHICLE_REGISTRATION_FILENAME);
        String subject = "Vehicle registration template test";
        Message message = buildMessageObjectForMail(subject, messageBody);
        getEmailToList().forEach(to -> sendMail(from,to,message));
    }

    private void sendMailForUserRegistration() {
        String from = "admin@britishchristian.com";

        String messageBody = MessageBody.getUserRegistrationMessage(StaticResources.USER_REGISTRATION_FILENAME);
        String subject = "User registration email";
        Message message = buildMessageObjectForMail(subject, messageBody);
        getEmailToList().forEach(to -> sendMail(from, to, message));
    }

    private Message buildMessageObjectForMail(String subject, String messageBody){
        Message message = new Message();
        message.setBody(buildEmailBody(messageBody));
        message.setSubject(buildSubjectForEmail(subject));
        return message;
    }

    private Content buildSubjectForEmail(String subject){
        Content content = new Content();
        content.setData(subject);
        return content;
    }

    private Body buildEmailBody(String messageBody){
        Content content = new Content();
        content.setData(messageBody);
        Body body = new Body();
        body.setHtml(content);
        return body;
    }

    private void sendMail(String from,String to,Message message){
        AmazonSimpleEmailService client = null;
        List<String> t = new ArrayList<>();
        t.add(to);
        SendEmailRequest request = new SendEmailRequest().withSource(from)
                .withDestination(new Destination(t))
                .withMessage(message);

        try {
            if (client == null) {
                client = AmazonSimpleEmailServiceClientBuilder.standard()
                        .build();
            }
            client.sendEmail(request); // this is where the exception is thrown
            System.out.println("Email   sent!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }

    private List<String> getEmailToList(){
        List<String> destinationAddresses = new ArrayList<String>();
        destinationAddresses.add("dan_nwankwo@yahoo.co.uk");
        destinationAddresses.add("danbeks74@gmail.com");
        destinationAddresses.add("danoski74@hotmail.com");
//        destinationAddresses.add("vedrtest@gmail.com");
        return destinationAddresses;
    }
}
