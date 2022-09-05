package br.com.guimadev.todoapp.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.guimadev.todoapp.model.Project;

/**
 *
 * @author Luis Felipe Guimarães Pinto
 */
class ProjectControllerTest {

	private static ProjectController projectController;
	private static Project project;

	@BeforeAll
	public static void init() {
		projectController = new ProjectController();
		project = new Project("Project Test", "Automated test with junit");
	}

	@Test
	void mustCreateANewRegisterInTheDatabase() {
		project = assertDoesNotThrow(() -> projectController.save(project));
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
		project.setName("Updated Name");
		assertDoesNotThrow(() -> projectController.update(project));
	}

	@Test
	void mustDeleteARegisterInTheDatabase() {
		assertDoesNotThrow(() -> projectController.removeById(project.getId()));
	}

}
