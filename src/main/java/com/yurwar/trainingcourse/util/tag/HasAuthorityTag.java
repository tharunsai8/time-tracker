package com.yurwar.trainingcourse.util.tag;

import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;

/**
 * Tag that checks if user has authority to get access to tag body
 *
 * @author Yurii Matora
 */
public class HasAuthorityTag extends TagSupport {
    private String authority = "";

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
