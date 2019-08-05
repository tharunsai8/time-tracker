package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityRequestService;
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
        final ActivityRequestService activityRequestService = new ActivityRequestService();
        commandMap.put("/login", new LoginCommand(userService));
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/registration", new RegistrationCommand(userService));
        commandMap.put("/users", new UsersCommand(userService));
        commandMap.put("/index", new HomeCommand());
        commandMap.put("/users/delete", new UserDeleteCommand(userService));
        commandMap.put("/users/update", new UserUpdateCommand(userService));
        commandMap.put("/activities", new ActivitiesCommand(activityService));
        commandMap.put("/activities/add", new ActivityAddCommand(activityService));
        commandMap.put("/activities/request", new ActivityRequestsCommand(activityRequestService));
        commandMap.put("/activities/delete", new ActivityDeleteCommand(activityService));
        commandMap.put("/activities/mark-time", new MarkTimeCommand(activityService));
        commandMap.put("/profile", new UserProfileCommand(userService));
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
