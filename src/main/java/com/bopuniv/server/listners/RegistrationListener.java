package com.bopuniv.server.listners;

import java.util.UUID;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.exceptions.EnvironmentVariableException;
import com.bopuniv.server.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    static final String CONFIGSET = "ConfigSet";

    @Value("${support.email}")
    private String supportEmail;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, "You registered successfully. Before you can use your account you will need to verify your email. Please click on the link below to verify your account.", event.getLocale());

        emailViaAmazonSES(user.getEmail(),
                "You successfully registered",
                message + " \r\n" + confirmationUrl,
                messages.getMessage("message.regSucc.subject", null, event.getLocale()),
                supportEmail);
    }

    private void emailViaAmazonSES(String TO, String HTMLBODY, String TEXTBODY, String SUBJECT, String FROM){

            String accessKeyId = env.getProperty("aws.accessKeyId");
            String secretKey = env.getProperty("aws.secretKey");

            try{
                if(accessKeyId == null){
                    throw new EnvironmentVariableException("Env variable aws.accessKeyId is null");
                }

                if(secretKey == null){
                    throw new EnvironmentVariableException("Env variable aws.secretKey is null");
                }

                AmazonSimpleEmailService client =
                        AmazonSimpleEmailServiceClientBuilder.standard()
                                .withCredentials(
                                        new AWSStaticCredentialsProvider(
                                                new BasicAWSCredentials(
                                                        accessKeyId, secretKey)))
                                // Replace US_WEST_2 with the AWS Region you're using for
                                // Amazon SES.
                                .withRegion(Regions.EU_WEST_2).build();
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(
                                new Destination().withToAddresses(TO))
                        .withMessage(new Message()
                                .withBody(new Body()
//                                    .withHtml(new Content()
//                                            .withCharset("UTF-8").withData(HTMLBODY))
                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(TEXTBODY)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(SUBJECT)))
                        .withSource(FROM);
                // Comment or remove the next line if you are not using a
                // configuration set
//                    .withConfigurationSetName(CONFIGSET);
                client.sendEmail(request);
                System.out.println("Email sent!");
            }catch (Exception e){
                System.out.println("Email not sent. "+e.getMessage());
            }


    }



}