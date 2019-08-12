package com.yurwar.trainingcourse.controller.dto;

/**
 * Data transfer object to transport user data from registration command to service
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.User
 * @see com.yurwar.trainingcourse.controller.command.Command
 * @see com.yurwar.trainingcourse.controller.command.RegistrationCommand
 */
public class RegistrationUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    public static Builder builder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    /**
     * Builder for class
     *
     * @see RegistrationUserDTO
     */
    public static class Builder {

        private String username;
        private String password;
        private String firstName;
        private String lastName;

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

        public RegistrationUserDTO build() {
            RegistrationUserDTO userDTO = new RegistrationUserDTO();
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);

            return userDTO;
        }
    }


}
