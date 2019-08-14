package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ActivitiesCommandTest {
    private ActivitiesCommand activitiesCommand;
    private ActivityService activityService;

    @Before
    public void init() {
        activityService = mock(ActivityService.class);
        activitiesCommand = new ActivitiesCommand(activityService);
    }

    @Test
    public void execute() {
        String expectedPath = "/WEB-INF/pages/activities.jsp";

        doReturn(10L).when(activityService).getNumberOfRecords();

        HttpServletRequest request = mock(HttpServletRequest.class);

        String path = activitiesCommand.execute(request);

        assertEquals(expectedPath, path);
    }

    @Test
    public void executeFail() {
        String expectedPath = "/WEB-INF/error/404.jsp";

        HttpServletRequest request = mock(HttpServletRequest.class);

        doReturn("invalid data").when(request).getParameter("page");

        String path = activitiesCommand.execute(request);

        assertEquals(expectedPath, path);
    }
}