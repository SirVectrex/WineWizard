package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.RiddleResponse;
import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import com.winewizard.winewizard.repository.WineProjectionI;
import com.winewizard.winewizard.service.EmailServiceI;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.winewizard.winewizard.service.HtmlFileReaderService;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailServiceI {

    @Autowired
    private JavaMailSender javaMailSender;
    private final HtmlFileReaderService htmlFileReaderService;

    private WineServiceImpl wineService;
    private WineryServiceImpl wineryService;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(HtmlFileReaderService htmlFileReaderService, WineServiceImpl wineService, WineryServiceImpl wineryService) {
        this.htmlFileReaderService = htmlFileReaderService;
        this.wineService = wineService;
        this.wineryService =wineryService;
    }

    public Void sendHtmlMail(String recipient) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String url = "https://riddles-api.vercel.app/random";
            RestTemplate restTemplate = new RestTemplate();

            RiddleResponse riddle = restTemplate.getForObject(url, RiddleResponse.class);

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("Newsletter");

            // HTML content for the email body
            String htmlBody = htmlFileReaderService.readHtmlFile("classpath:templates/general/newsletter.html");

            htmlBody = htmlBody.replace("riddle_placeholder", riddle.getRiddle());
            htmlBody = htmlBody.replace("answer_placeholder", riddle.getAnswer());

            List<WineProjectionI> winelist = wineService.getWineRatings();

            // build big String with new lines for each wine
            String topWines = "";
            for (WineProjectionI wine : winelist) {
                topWines += wine.getName() + " " + wine.getAvgTasteRating() + " " + wine.getAvgDesignRating() + " " + wine.getAvgPriceRating() + "\n";
            }

            // build an html table with the wines and add a header as well pls
            String htmltext = "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>Taste</th>\n" +
                    "    <th>Design</th>\n" +
                    "    <th>Price</th>\n" +
                    "  </tr>\n";

            for (WineProjectionI wine : winelist) {
                htmltext += "<tr>\n" +
                        "    <td>" + wine.getName() + "</td>\n" +
                        "    <td>" + wine.getAvgTasteRating() + "</td>\n" +
                        "    <td>" + wine.getAvgDesignRating() + "</td>\n" +
                        "    <td>" + wine.getAvgPriceRating() + "</td>\n" +
                        "  </tr>\n";
            }

            htmltext +="</table>";

            htmlBody = htmlBody.replace("Top_new_wine_placeholder", htmltext);

            List<Wine> allWines = wineService.getAllWines();
            List<Winery> allWinerys = wineryService.getAllWineries();


            // Zufällige Index generieren
            int randomIndex = new Random().nextInt(allWines.size());

            Wine wine_of_the_week = allWines.get(randomIndex);

            htmltext =wine_of_the_week.getName() + ". "+ wine_of_the_week.getType() + ". " + wine_of_the_week.getDescription();

            htmlBody = htmlBody.replace("Wine_of_the_week_placeholder", htmltext);

            randomIndex = new Random().nextInt(allWinerys.size());

            Winery winery_of_the_week = allWinerys.get(randomIndex);

            htmltext =winery_of_the_week.getWineryName();

            htmlBody = htmlBody.replace("Vintner_of_the_week_placeholder", htmltext);

            // Set the HTML content to true
            helper.setText(htmlBody, true);
            // Send the email
            javaMailSender.send(mimeMessage);

        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Void sendTopWines(String recipient){

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String url = "https://riddles-api.vercel.app/random";
            RestTemplate restTemplate = new RestTemplate();

            RiddleResponse riddle = restTemplate.getForObject(url, RiddleResponse.class);

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("WineWizard - Our best wines");

            // HTML content for the email body
            String htmlBody = htmlFileReaderService.readHtmlFile("classpath:templates/general/topWineList.html");

            List<WineProjectionI> winelist = wineService.getWineRatings();

            // build big String with new lines for each wine
            String topWines = "";
            for (WineProjectionI wine : winelist) {
                topWines += wine.getName() + " " + wine.getAvgTasteRating() + " " + wine.getAvgDesignRating() + " " + wine.getAvgPriceRating() + "\n";
            }

            // build an html table with the wines and add a header as well pls
            String htmltext = "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>Taste</th>\n" +
                    "    <th>Design</th>\n" +
                    "    <th>Price</th>\n" +
                    "  </tr>\n";

            for (WineProjectionI wine : winelist) {
                            htmltext += "<tr>\n" +
                                "    <td>" + wine.getName() + "</td>\n" +
                                "    <td>" + wine.getAvgTasteRating() + "</td>\n" +
                                "    <td>" + wine.getAvgDesignRating() + "</td>\n" +
                                "    <td>" + wine.getAvgPriceRating() + "</td>\n" +
                                "  </tr>\n";
            }

            htmltext +="</table>";

            htmlBody = htmlBody.replace("Top_wine_placeholder", htmltext);

            // Set the HTML content to true
            helper.setText(htmlBody, true);

            // Send the email
            javaMailSender.send(mimeMessage);

        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Void sendVerificaiton(User recipient) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(recipient.getEmail());
            helper.setSubject("WineWizard - Registration");

            String verifyURL = "http://localhost:8080/verify?code=" + recipient.getVerificationCode();

            String content = "Hi " + recipient.getLogin() + ",<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\""  + verifyURL
                    + "\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "Your wine wizard.";

            // Set the HTML content to true
            helper.setText(content, true);

            // Send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}