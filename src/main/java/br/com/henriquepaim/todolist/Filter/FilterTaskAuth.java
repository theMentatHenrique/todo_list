package br.com.henriquepaim.todolist.Filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.henriquepaim.todolist.User.IUserRepository;
import br.com.henriquepaim.todolist.User.UserModel;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            String authorization = request.getHeader("Authorization");
            String subs = authorization.substring("Basic".length()).trim();
            byte[] decode = Base64.getDecoder().decode(subs);

            String decodificado = new String(decode);
            String[] split = decodificado.split(":");
            String userName = split[0];
            String password = split[1];
            UserModel byName = userRepository.findByName(userName);

            if(byName == null) {
                response.sendError(401);
                return;
            }
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), byName.getPassword());

            if (result.verified) {
                request.setAttribute("userId", byName.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
