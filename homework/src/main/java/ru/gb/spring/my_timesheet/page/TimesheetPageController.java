package ru.gb.spring.my_timesheet.page;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.spring.my_timesheet.service.TimesheetPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/timesheets")
@RequiredArgsConstructor // аннотация Lombok, чтобы не писать код конструктора для класса(включает только поля final).
// Аннотация @AllArgsConstructor - включает все поля
//@PreAuthorize("hasRole(\"admin\")") // SPel - spring expression language
//@Secured({"admin"}) // эта аннотация говорит, что к этому эндпоинту имеет доступ только admin (тогда можно не прописывать
// в SecurityConfiguration в requestMatchers. Важно! Проверяет по hasrole! Можно ставить над всем контроллером,
// либо над каждым эндпоинтом отдельно. У аннотации приоритет перед requestMatchers в SecurityConfiguration
public class TimesheetPageController {

    private final TimesheetPageService service;

    // Вместо конструктора, можно использовать @RequiredArgsConstructor. Если поля выше не final,
    // то они не будут включены в конструктор (переданы в качестве параметров)
//  public TimesheetPageController(TimesheetService service) {
//    this.service = service;
//  }

    @GetMapping
//    @Secured({"admin", "user"})
    public String getAllTimesheets(Model model) {
        List<TimesheetPageDto> timesheets = service.findAll();
        model.addAttribute("timesheets", timesheets);
        return "timesheets-page.html";
    }

    // GET /home/timesheets/{id}
    @GetMapping("/{id}")
    public String getTimesheetPage(@PathVariable Long id, Model model) { //Model - интерфейс, ~ HashMap, важен метод addAttribute
        Optional<TimesheetPageDto> timesheetOpt = service.findById(id);
        if (timesheetOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        model.addAttribute("timesheet", timesheetOpt.get());
        return "timesheet-page.html";
    }
}
