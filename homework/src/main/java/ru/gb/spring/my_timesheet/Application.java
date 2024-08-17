package ru.gb.spring.my_timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.spring.my_timesheet.aspect.Recover;
import ru.gb.spring.my_timesheet.model.*;
import ru.gb.spring.my_timesheet.repository.*;
import ru.gb.spring.my_timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		// 1. пользователь регистрируется на сайте и вводит свой пароль
		// 1.1 сервер считает хеш от пароля и сохраняет его в бд = hashInDatabase
		// 2. пользователь логинится на сайт и вводит свой пароль
		// 2.1 сервер считает хеш от пароля и сравнивает его с хешом в БД
		// hashFunc(password) -> hashInDatabase
		// hash -> password !!!
		// username1, password => hash(username1 + _ + password) == hash1
		// username2, password => hash(username2 + _ + password) == hash2

		// hashFunc(rawPassword) == hashInDatabase

		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		Role roleAdmin = new Role();
		roleAdmin.setName(RoleName.ADMIN);
		roleRepository.save(roleAdmin);

		Role roleUser = new Role();
		roleUser.setName(RoleName.USER);
		roleRepository.save(roleUser);

		Role roleAnonymous = new Role();
		roleAnonymous.setName(RoleName.ANONYMOUS);
		roleRepository.save(roleAnonymous);

		Role roleRest = new Role();
		roleRest.setName(RoleName.REST);
		roleRepository.save(roleRest);

		UserRepository userRepository = ctx.getBean(UserRepository.class);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin - (bcrypt-generator.com) Хэш от admin - то,
		// что будет храниться в БД. При вводе верного пароля (admin), он будет преобразован в хэш, который должен соответствовать тому, что сохранен.

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$zRccUkXS1abZxoL6MYRHx.wJENZgeLJSkrIQYYxEAVj8jqPC.wgdW"); // user - Хэш от user

		User anonymous = new User();
		anonymous.setLogin("anon");
		anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon - Хэш от anon

		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("$2a$12$/8Xwei8n8/PkBtxotUzDPuhP/ODGwY2aCJLIcIpiR0z/qQhLfZNMK");

		admin = userRepository.save(admin);
		user = userRepository.save(user);
		anonymous = userRepository.save(anonymous);
		rest = userRepository.save(rest);

		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		// id user_id role_name
		//  1       1     admin
		//  2       1     user
		//  3       1     rest
		//  4       2     user
		//  5       2     rest
		//  6       3     rest
		UserRole adminAdminRole = new UserRole();
		adminAdminRole.setUser(admin);
		adminAdminRole.setRole(roleRepository.findByName(RoleName.ADMIN));
		userRoleRepository.save(adminAdminRole);

		UserRole adminUserRole = new UserRole();
		adminUserRole.setUser(admin);
		adminUserRole.setRole(roleRepository.findByName(RoleName.USER));
		userRoleRepository.save(adminUserRole);

		UserRole adminRestRole = new UserRole();
		adminRestRole.setUser(admin);
		adminRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(adminRestRole);

		UserRole userUserRole = new UserRole();
		userUserRole.setUser(user);
		userUserRole.setRole(roleRepository.findByName(RoleName.USER));
		userRoleRepository.save(userUserRole);

		UserRole userRestRole = new UserRole();
		userRestRole.setUser(user);
		userRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(userRestRole);

		UserRole restRestRole = new UserRole();
		restRestRole.setUser(rest);
		restRestRole.setRole(roleRepository.findByName(RoleName.REST));
		userRoleRepository.save(restRestRole);

		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setName("Project #" + i);
			projectRepo.save(project);
		}

		EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);
		Generator generator = new Generator();
		for (int i = 1; i <= 5; i++) {
			Employee employee = new Employee();
			employee.setFirstName(generator.generateMaleFirstName());
			employee.setLastName(generator.generateMaleLastName());
			employee.setPhone(generator.generatePhone());
			employee.setDepartment(generator.generateDepartment());
			employeeRepo.save(employee);
		}

//		EmployeeProjectRepository employeeProjectRepository = ctx.getBean(EmployeeProjectRepository.class);
//		for (int i = 1; i <=10 ; i++) {
//			EmployeeProject employeeProject = new EmployeeProject();
//			employeeProject.setProject(projectRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1, 6)));
//			employeeProject.setEmployee(employeeRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1,6)));
//
//			employeeProjectRepository.save(employeeProject);
//		}

		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

		LocalDateTime createdAt = LocalDateTime.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1).plusHours(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProject(projectRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1, 6)));
			timesheet.setEmployee(employeeRepo.getReferenceById(ThreadLocalRandom.current().nextLong(1,6)));
//			timesheet.setCreatedAt(createdAt));
			timesheet.setCreatedAt(LocalDate.from(createdAt));
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepo.save(timesheet);
		}

		System.out.println("__________________________________________________________________________________");
		System.out.println(getElement(6));
	}
	@Recover
	static int getElement(int num){
		int[] array = {1,2,3,4,5};
		return array[num];
	}
}
