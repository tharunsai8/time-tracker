package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;
import com.yurwar.trainingcourse.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static CommandManager commandManager;
    private final Map<String, Command> commandMap = new HashMap<>();

    private CommandManager() {
        final UserService userService = new UserService();
        final ActivityService activityService = new ActivityService();
        commandMap.put("/login", new LoginCommand(new UserService()));
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/registration", new RegistrationCommand(new UserService()));
        commandMap.put("/users", new UsersCommand(new UserService()));
        commandMap.put("/index", new HomeCommand());
        commandMap.put("/users/delete", new UserDeleteCommand(new UserService()));
        commandMap.put("/users/update", new UserUpdateCommand(new UserService()));
        commandMap.put("/activities", new ActivitiesCommand(new ActivityService()));
        commandMap.put("/activities/add", new ActivityAddCommand(new ActivityService()));
        commandMap.put("/profile", new UserProfileCommand(new UserService()));
    }

    public static CommandManager getInstance() {
        if (commandManager == null) {
            synchronized (CommandManager.class) {
                if (commandManager == null) {
                    commandManager = new CommandManager();
                }
            }
        }
        return commandManager;
    }

    public Command getCommand(String commandName) {
        return commandMap.getOrDefault(commandName, r -> "/index.jsp");
    }
}
