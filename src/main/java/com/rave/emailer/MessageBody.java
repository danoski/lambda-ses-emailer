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
import java.util.stream.Stream;

/**
 * Created by Useradmin on 23/06/2017.
 */
public class MessageBody {

    public static String getMessage3(String filename) {
        Stream<String> fileStream = null;
        StringBuilder contents = new StringBuilder();

        AmazonS3 client = new AmazonS3Client();
        S3Object xFile = client.getObject(StaticResources.S3_BUCKET_NAME,
                filename);
        InputStream inputStream = xFile.getObjectContent();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        fileStream = buffer.lines();
        fileStream.forEach(s -> contents.append(s.toString()));
        return contents.toString();
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

        String templateString = "The ${animal} jumped over the ${target}.";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
}
