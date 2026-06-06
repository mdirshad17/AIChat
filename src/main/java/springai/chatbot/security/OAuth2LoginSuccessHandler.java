package springai.chatbot.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Value("${app.appUrl}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. Get the user profile from Google/GitHub
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Extract email (fallback to 'login' for GitHub)
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            email = oAuth2User.getAttribute("login");
        }

        // 2. Mint the custom JWT
        String token = jwtService.generateToken(email);

        // 3. Redirect to the React frontend, passing the token in the URL
        // The frontend will grab this from the URL, save it, and attach it to future requests
        String targetUrl = frontendUrl + "/oauth2/redirect?token=" + token;
        response.sendRedirect(targetUrl);
    }
}