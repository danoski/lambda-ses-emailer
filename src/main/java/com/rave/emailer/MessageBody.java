package com.rave.emailer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.lang.text.StrSubstitutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Useradmin on 23/06/2017.
 */
public class MessageBody {

    public static String getVehicleRegistrationMessage(String filename) {
        String template = MessageBody.getMessageTemplate(filename);
        return MessageBody.VehicleRegistrationTemplateResolver(template,
                StaticResources.CREST_IMAGE_PATH,
                StaticResources.CROWN_IMAGE_PATH,
                StaticResources.REG_NAME,
                StaticResources.REG_DATE,
                StaticResources.RETAILER_NAME,
                StaticResources.RETAILER_ADDR_1,
                StaticResources.RETAILER_ADDR_2,
                StaticResources.RETAILER_ADDR_3,
                StaticResources.RETAILER_TOWN,
                StaticResources.RETAILER_COUNTY,
                StaticResources.RETAILER_POSTCODE
                );
    }

    public static String getUserRegistrationMessage(String filename) {
        String template = MessageBody.getMessageTemplate(filename);
        return MessageBody.UserRegistrationTemplateResolver(template,
                StaticResources.CREST_IMAGE_PATH,
                StaticResources.CROWN_IMAGE_PATH,
                StaticResources.REG_NAME,
                StaticResources.USERREG_USER_NAME,
                StaticResources.RESET_HREF
                );
    }

    public static String getPasswordResetMessage(String filename) {
        String template = MessageBody.getMessageTemplate(filename);
        return MessageBody.passwordResetEmailTemplateResolver(template,
                StaticResources.CREST_IMAGE_PATH,
                StaticResources.CROWN_IMAGE_PATH,
                StaticResources.RESET_HREF);
    }

    public static String getMessageTemplate(String filename){
        Stream<String> fileStream = null;
        StringBuilder contents = new StringBuilder();

        AmazonS3 client = new AmazonS3Client();
        S3Object xFile = client.getObject(StaticResources.S3_BUCKET_NAME,
                filename);
        InputStream inputStream = xFile.getObjectContent();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        fileStream = buffer.lines();
        fileStream.forEach(s -> contents.append(s.toString()));
        Optional<String> optionalString = Optional.ofNullable(contents.toString());
        return optionalString.map(String::toString).orElse("");
    }

    public static String passwordResetEmailTemplateResolver(String template,
                                                            String crestImagePath,
                                                            String crownImagePath,
                                                            String resetHref
                                                            ) {
        Map valuesMap = new HashMap();
        valuesMap.put("registration.crestImage", crestImagePath);
        valuesMap.put("registration.headerImage", crownImagePath);
        valuesMap.put("password.reset.href", resetHref);
        valuesMap.put("registration.date", "29th June 2017");

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }

    public static String VehicleRegistrationTemplateResolver(String template,
                                                            String crestImagePath,
                                                            String crownImagePath,
                                                            String name,
                                                             String date,
                                                             String retailerName,
                                                             String address1,
                                                             String address2,
                                                             String address3,
                                                             String town,
                                                             String county,
                                                             String postcode
                                                            ) {
        Map valuesMap = new HashMap();
        valuesMap.put("registration.crestImage", crestImagePath);
        valuesMap.put("registration.headerImage", crownImagePath);
        valuesMap.put("registration.name", name);
        valuesMap.put("registration.date", date);
        valuesMap.put("retailer.name", retailerName);
        valuesMap.put("retailer.address.line1", address1);
        valuesMap.put("retailer.address.line2", address2);
        valuesMap.put("retailer.address.line3", address3);
        valuesMap.put("retailer.address.town", town);
        valuesMap.put("retailer.address.county", county);
        valuesMap.put("retailer.address.postcode", postcode);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }

    public static String UserRegistrationTemplateResolver(String template,
                                                            String crestImagePath,
                                                            String crownImagePath,
                                                            String name,
                                                             String userName,
                                                             String passwordUrl
                                                            ) {
        Map valuesMap = new HashMap();
        valuesMap.put("registration.crestImage", crestImagePath);
        valuesMap.put("registration.headerImage", crownImagePath);
        valuesMap.put("registration.name", name);
        valuesMap.put("registration.username", userName);
        valuesMap.put("registration.passwordGenerationUrl", passwordUrl);


        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
}
