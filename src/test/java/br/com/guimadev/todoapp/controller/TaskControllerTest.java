package br.com.guimadev.todoapp.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.guimadev.todoapp.model.Project;
import br.com.guimadev.todoapp.model.Task;

/**
 *
 * @author Luis Felipe Guimarães Pinto
 */
class TaskControllerTest {

	private static ProjectController projectController;
	private static Project project;
	private static TaskController taskController;
	private static Task task;

	@BeforeAll
	public static void init() {
		projectController = new ProjectController();
		project = projectController.save(new Project("Project Test", "Automated test with junit"));
		taskController = new TaskController();
		task = new Task(project.getId(), "Task Test", "Automated test with junit", "nota", new Date());
	}

	@Test
	void mustCreateANewRegisterInTheDatabase() {
		task = assertDoesNotThrow(() -> taskController.save(task));
	}

	@Test
	void shouldNotReturnAnEmptyList() {
		assertEquals(false, projectController.getAll().isEmpty());
	}

	@Test
	void shouldReturnAnArrayList() {
		assertEquals(ArrayList.class, projectController.getAll().getClass());
	}

	@Test
	void mustUpdateARegisterInTheDatabase() {
		task.setName("Updated Name");
		assertDoesNotThrow(() -> taskController.update(task));
	}

	@Test
	void mustDeleteARegisterInTheDatabase() {
		assertDoesNotThrow(() -> taskController.removeById(task.getId()));
	}

	@AfterAll
	public static void finish() {
		projectController.removeById(project.getId());
	}

}
