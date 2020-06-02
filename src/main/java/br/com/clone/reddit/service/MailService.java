package br.com.clone.reddit.service;

import br.com.clone.reddit.exceptions.SpringRedditException;
import br.com.clone.reddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender javaMailSender;

    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("email@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Verification email sent");
        } catch (MailException e) {
            throw new SpringRedditException("Exception occurred when sending mail to" + notificationEmail.getRecipient() + " more details:" + e);
        }
    }
}
