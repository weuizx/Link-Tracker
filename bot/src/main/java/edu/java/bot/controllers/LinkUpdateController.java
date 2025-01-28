package edu.java.bot.controllers;

import edu.java.bot.controllers.dto.NotifyUsersDtoIn;
import edu.java.bot.service.NotificationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LinkUpdateController {

    private final NotificationServiceImpl notifyService;

    @PostMapping("/updates")
    public void updateLinks(@Valid @RequestBody NotifyUsersDtoIn notifyUsersDtoIn) {

        notifyService.notifyUsers(notifyUsersDtoIn);
    }
}
