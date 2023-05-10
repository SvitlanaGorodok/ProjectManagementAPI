package api.projectmanagement.controller;

import api.projectmanagement.model.dto.EmployeeDto;
import api.projectmanagement.model.dto.FindProjectParam;
import api.projectmanagement.model.dto.ProjectDto;
import api.projectmanagement.service.EmployeeService;
import api.projectmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/projects")
@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView model = new ModelAndView("projects/showall");
        model.addObject("projects", projectService.findAll());
        return model;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView model = new ModelAndView("projects/create");
        model.addObject("employees", employeeService.findAll());
        model.addObject("projectNames", projectService.findAllNames());
        return model;
    }

    @PostMapping("/create")
    public RedirectView create(@Validated @ModelAttribute("projectDto") ProjectDto projectDto) {
        projectService.save(projectDto);
        return new RedirectView("/projects");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id) {
        projectService.deleteById(id);
        return new RedirectView("/projects");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("projects/update");
        ProjectDto project = projectService.findById(id);
        model.addObject("project", project);
        model.addObject("employees", employeeService.findAll());
        model.addObject("projectNames", projectService.findAllNames().stream()
                .filter(n -> !n.equals(project.getName()))
                .collect(Collectors.toList()));
        return model;
    }

    @PostMapping("/update")
    public RedirectView update(@Validated @ModelAttribute("employeeDto") ProjectDto projectDto) {
        projectService.save(projectDto);
        return new RedirectView("/projects");
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("projects/details");
        ProjectDto project = projectService.findById(id);
        model.addObject("project", project);
        List<EmployeeDto> employees = employeeService.findAll().stream()
                .filter(e -> project.getEmployeeIds().contains(e.getId()))
                .collect(Collectors.toList());
        model.addObject("employees", employees);
        return model;
    }

    @GetMapping("/find")
    public ModelAndView findForm() {
        return new ModelAndView("projects/find");
    }

    @PostMapping("/find")
    public ModelAndView find(@Validated @ModelAttribute("findProjectParam") FindProjectParam findProjectParam) {
        ModelAndView model = new ModelAndView("projects/find");
        model.addObject("projects", projectService.findByParameters(findProjectParam));
        return model;
    }
}
