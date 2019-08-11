package com.yurwar.trainingcourse.util;

import com.yurwar.trainingcourse.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CommandUtils {
    public static Locale getLocaleFromSession(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        return lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
    }

    public static User getUserFromSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("authUser");
    }
}
