package com.yurwar.trainingcourse.controller.dto;

import com.yurwar.trainingcourse.model.entity.Authority;

import java.util.HashSet;
import java.util.Set;

/**
 * Data transfer object to transport user data from update command to service
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.User
 * @see com.yurwar.trainingcourse.controller.command.Command
 * @see com.yurwar.trainingcourse.controller.command.UserUpdateCommand
 * @see com.yurwar.trainingcourse.controller.command.UserProfileUpdateCommand
 */
public class UpdateUserDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Authority> authorities = new HashSet<>();

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Builder for class
     *
     * @see UpdateUserDTO
     */
    public static class Builder {

        private long id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private Set<Authority> authorities;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder authorities(Set<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UpdateUserDTO build() {
            UpdateUserDTO userDTO = new UpdateUserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setAuthorities(authorities);

            return userDTO;
        }
    }
}
