package com.rave.emailer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Useradmin on 23/06/2017.
 */
public class MessageBody {
    private String body = "";

    public static String getBody(){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "        <title>Register a Vehicle service - user registration email</title>\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    </head>\n" +
                "\n" +
                "    <body style=\"line-height: 24px; text: #0B0C0C; font-size: 17px; font-family: Helvetica\">\n" +
                "        <table width=\"100%\">\n" +
                "            <tr>\n" +
                "                <td colspan=3 style=\"background-color:black; height: 30px\">\n" +
                "                    <table width=\"100%\">\n" +
                "                        <tr>\n" +
                "                            <td width=\"20%\"></td>\n" +
                "                            <td width=\"60%\">\n" +
                "                                <table>\n" +
                "                                    <tr>\n" +
                "                                        <td>\n" +
                "                                            <img alt=\"gov uk crown\" align=\"middle\" style=\"width:40px; height:35px\" src=\"${registration.headerImage}\"/>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                            <td width=\"20%\"></td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr/>\n" +
                "            <tr>\n" +
                "                <td width=\"20%\"></td>\n" +
                "                <td width=\"60%\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                <table>\n" +
                "                                    <tr>\n" +
                "                                        <td>\n" +
                "                                            <img alt=\"gov uk crest\" align=\"middle\" style=\"width:40px; height:35px\" src=\"${registration.crestImage}\"/>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "\n" +
                "                                <br/>\n" +
                "                                <p>\n" +
                "                                    <b>Please do not reply, this is an automated email.\n" +
                "                                        <br/>Responses to this email address cannot be received.\n" +
                "                                    </b>\n" +
                "                                </p>\n" +
                "\n" +
                "                                <hr/>\n" +
                "\n" +
                "                                <p>Your individual account has now been created for the Register a Vehicle service.\n" +
                "                                    <br/>Please click on the link below and follow the steps to activate your account.</p>\n" +
                "\n" +
                "                                <p>This link should only be used by:</p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                <table style=\"padding: 10px 0 10px 20px\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" width=\"60%\" bgcolor=\"#dcdcdc\">\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"border-collapse: collapse; padding: 0 15px 10px 15px; width: 70px\">Name:</td>\n" +
                "                                        <td style=\"border-collapse: collapse; padding: 0 15px 10px 15px;\"><b>${registration.name}</b></td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"border-collapse: collapse; padding: 0 15px 10px 15px; width: 70px\">User ID:</td>\n" +
                "                                        <td style=\"border-collapse: collapse; padding: 0 15px 10px 15px;\"><b>${registration.username}</b></td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                <p>You can only use this link once and it will become inactive after 72 hours.</p>\n" +
                "                                <p><a href=\"${registration.passwordGenerationUrl}\">${registration.passwordGenerationUrl}</a></p>\n" +
                "                                <p>If you're having any problems with your security questions or need to request a new link, you can contact the DVLA helpdesk on 0300 1231345.</p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td width=\"20%\"></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </body>\n" +
                "</html>";
    }

    public String getMessage(String fileName){
        Stream<String> fileStream = null;
        StringBuilder contents = new StringBuilder();
        try {
            URI uri = getClass().getClassLoader().getResource(fileName).toURI();
            fileStream = Files.lines(Paths.get(uri));
            System.out.println("Inside getMessage() -- printing contents");
            fileStream.forEach(s -> System.out.println(s));
//            fileStream.forEach(s -> contents.append(s.toString()));
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
        } catch (URISyntaxException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
        }
        return contents.toString();
    }

    public String getMessage2(String fileName){
        Stream<String> fileStream = null;
        StringBuilder contents = new StringBuilder();
        try {
            System.out.println("Try to get class laoder");
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            if (cl == null) {
                System.out.println("Class loader is NULL");
            }else{
                System.out.println("Class loader is NOT NULL");
            }
            System.out.println("try to get input stream");
            InputStream inputStream = cl.getResourceAsStream(fileName);
            System.out.println("Input Stream GOT");
            if (inputStream == null) {
                System.out.println("Input Stream is null");
            }else{
                System.out.println("Is NOT NULL inputstream");
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            fileStream =  buffer.lines();

            /*
            URI uri = cl.getSystemResource(fileName).toURI();
            if (uri == null) {
                System.out.println("URI is NULL");
            }else{
                System.out.println("URI is NOT NULL");
                System.out.println(uri.toString());
            }
            fileStream = Files.lines(Paths.get(uri));*/
            System.out.println("Inside getMessage() -- printing contents");
            fileStream.forEach(s -> System.out.println(s));
//            fileStream.forEach(s -> contents.append(s.toString()));
        } /*catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
        } catch (URISyntaxException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
        }*/ catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackElement : e.getStackTrace()) {
                stringBuilder.append(stackElement.toString());
                stringBuilder.append("\n");
            }
            System.out.println("Exception in Message2\n");
            System.out.println(e.getMessage());
            System.out.println(stringBuilder.toString());
        }
        return contents.toString();
    }

    public  String getMessage3(String filename){
        Stream<String> fileStream = null;
        StringBuilder contents = new StringBuilder();

        AmazonS3 client = new AmazonS3Client();
        S3Object xFile = client.getObject("senegy_software_solution",
                "ved.keeper.vehicle.registration.tpl.html");
        InputStream inputStream = xFile.getObjectContent();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        fileStream =  buffer.lines();

//        fileStream.forEach(s -> System.out.println(s));
        fileStream.forEach(s -> contents.append(s.toString()));
        return contents.toString();
    }
}
