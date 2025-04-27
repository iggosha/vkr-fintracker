package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.golovkov.fintracker.service.MoneyFlowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flows")
public class MoneyFlowController {

    private final MoneyFlowService moneyFlowService;

}
