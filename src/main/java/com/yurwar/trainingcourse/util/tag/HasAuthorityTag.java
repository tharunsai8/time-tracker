package com.yurwar.trainingcourse.util.tag;

import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;

public class HasAuthorityTag extends TagSupport {
    String authority = "";

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public int doStartTag() throws JspException {

        User authUser = (User) pageContext.getSession().getAttribute("authUser");

        if (Objects.nonNull(authUser) && authUser.getAuthorities().contains(Authority.valueOf(authority))) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
