package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.BCodec;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


//TODO Add multiply authorities
public class UserUpdateCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UserUpdateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] authorities = request.getParameterValues("authorities");

        log.info("First name: {}, Last name: {}, Username: {}, Password: {}, Authorities: {}",
                firstName, lastName, username, password, authorities);

        if (!ObjectUtils.allNotNull(firstName, lastName, username, password)) {
            User user = userService.findUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
            request.setAttribute("user", user);
            request.setAttribute("authorities", Authority.values());
            return "/update-user.jsp";
        }

        //TODO Check on blank
        //TODO Validate input data
        //TODO Check password

        User userDTO = new User();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPassword(DigestUtils.md5Hex(password));
        userDTO.setAuthorities(extractAuthorities(authorities));
        userService.updateUser(userDTO);
        return "redirect:/users";
    }

    private Set<Authority> extractAuthorities(String[] authorities) {
        return Arrays.stream(authorities).map(Authority::valueOf).collect(Collectors.toSet());
    }
}
