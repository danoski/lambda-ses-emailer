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
//        String bodyMessage = messageBody.getMessage("ved.keeper.vehicle.registration.tpl.html");
        String bodyMessage = templateResolver(messageBody.getMessage3("ved.keeper.vehicle.registration.tpl.html"));
        System.out.println("**The body message**\n");
        System.out.println(bodyMessage);
        System.out.println("**\nEnd body message**");
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

    private String templateResolver(String template){
        Map valuesMap = new HashMap();
        valuesMap.put("registration.number", "W44 WWW");
        valuesMap.put("registration.date", "29th June 2017");
        valuesMap.put("registration.crestImage", "https://s3.amazonaws.com/senegy_software_solution/bis_crest_27px_x2.png");
        valuesMap.put("registration.headerImage", "https://s3.amazonaws.com/senegy_software_solution/hmrc_crest_27px_x2.png");
        String templateString = "The ${animal} jumped over the ${target}.";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
}
