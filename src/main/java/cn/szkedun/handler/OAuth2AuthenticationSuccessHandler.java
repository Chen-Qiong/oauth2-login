package cn.szkedun.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User details = (User) authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        Map<String,Object> data =new HashMap<>();
        data.put("code",0);
        data.put("message","操作成功");
        data.put("data",details);
        response.getWriter().println( objectMapper.writeValueAsString(data));
    }
}
