package com.example.cvdev.service;

import com.example.cvdev.model.ConfirmationToken;
import com.example.cvdev.model.User;
import com.example.cvdev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailSenderService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {

        final Optional<User> optionalUser  = userRepository.findByEmail(email);

        if(optionalUser.isPresent())
        {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }

    }

    public void signUpUser(User user){

        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        final User createdUser = userRepository.save(user);

        final ConfirmationToken confimationToken = new ConfirmationToken(user);

        confirmationTokenService.saveConfirmationToken(confimationToken);

    }


    public void confirmUser(ConfirmationToken confimationToken)
    {
        final User user = confimationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfimationToken(confimationToken.getId());
    }

    void sendConfimationMail(String userMail, String token)
    {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confimation");
        mailMessage.setSubject("Mail Confim Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/sign-up/confirm?token="+ token);

        emailSenderService.sendEmail(mailMessage);
    }

}
