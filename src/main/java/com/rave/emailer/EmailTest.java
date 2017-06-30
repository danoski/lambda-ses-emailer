package com.rave.emailer;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Useradmin on 22/06/2017.
 */
public class EmailTest {
    public String sendEmail(ScheduledEvent scheduledEvent, Context context) {
        List<String> destinationAddresses = new ArrayList<String>();
        destinationAddresses.add("dan_nwankwo@yahoo.co.uk");
        destinationAddresses.add("danbeks74@gmail.com");
        destinationAddresses.add("danoski74@hotmail.com");
        String from = "admin@nigeriachristian.com";
        AmazonSimpleEmailService client = null;

        /*subject*/
        Content content = new Content();
        MessageBody messageBody = new MessageBody();
        String bodyMessage = MessageBody.getMessage3(StaticResources.PASSWORD_RESET_FILENAME);
        bodyMessage = MessageBody.passwordResetEmailTemplateResolver(bodyMessage,
                StaticResources.CREST_IMAGE_PATH,
                StaticResources.CROWN_IMAGE_PATH,
                StaticResources.RESET_HREF);

        content.setData(bodyMessage);
        Body body = new Body();
        body.setHtml(content);

        /*Body*/
        Content subjectContent = new Content();
        String subject = "Subject from lambda";
        subjectContent.setData(subject);

        /*Message*/
        Message message = new Message();
        message.setBody(body);
        message.setSubject(subjectContent);

        SendEmailRequest request = new SendEmailRequest().withSource(from)
                .withDestination(new Destination(destinationAddresses))
                .withMessage(message);

        try {
//            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

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

        return "done";
    }


}
