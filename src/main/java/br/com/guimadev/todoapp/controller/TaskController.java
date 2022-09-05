/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.guimadev.todoapp.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.guimadev.todoapp.model.Project;
import br.com.guimadev.todoapp.model.Task;
import br.com.guimadev.todoapp.util.ConnectionFactory;

/**
 *
 * @author Luis Felipe Guimarães Pinto
 */
public class TaskController {
    
    public Task save(Task task) {
        
        String query = "INSERT INTO tasks (idProject, name, description, completed, notes, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        List<Task> tasks;
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, task.getIdProject());
            pstm.setString(2, task.getName());
            pstm.setString(3, task.getDescription());
            pstm.setBoolean(4, task.isIsCompleted());
            pstm.setString(5, task.getNotes());
            pstm.setDate(6, new Date(task.getDeadline().getTime()));
            pstm.setDate(7, new Date(task.getCreatedAt().getTime()));
            pstm.setDate (8, new Date(task.getUpdatedAt().getTime()));
            pstm.execute();
            
            tasks = this.getAll(task.getIdProject());
            return tasks.get(tasks.size() - 1);
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir uma tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
            
        }
        
    }
    
    public void update(Task task) {
        
        String query = "UPDATE tasks SET idProject = ?, name = ?, description = ?, completed = ?, notes = ?, deadline = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
   
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, task.getIdProject());
            pstm.setString(2, task.getName());
            pstm.setString(3, task.getDescription());
            pstm.setBoolean(4, task.isIsCompleted());
            pstm.setString(5, task.getNotes());
            pstm.setDate(6, new Date(task.getDeadline().getTime()));
            pstm.setDate(7, new Date(task.getCreatedAt().getTime()));
            pstm.setDate(8, new Date(task.getUpdatedAt().getTime()));
            pstm.setInt(9, task.getId());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar uma tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
        }
        
    }
    
    public void removeById(int taskId) {
        
        String query = "DELETE FROM tasks WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, taskId);
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
        }
        
    }
    
        public void removeAllByProject(Project project) {
        
            List<Task> tasks = getAll(project.getId());
            
            tasks.forEach(t -> {
               
            removeById(t.getId());

            });

    }
    
    public List<Task> getAll (int idProject) {
        
        String query = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset= null;
        
        List<Task> tasks = new ArrayList<>();
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, idProject);
            rset = pstm.executeQuery();
            
            while (rset.next()) {
                Task task = new Task();
                task.setId(rset.getInt("id"));
                task.setIdProject(rset.getInt("idProject"));
                task.setName(rset.getString("name"));
                task.setDescription(rset.getString("description"));
                task.setNotes(rset.getString("notes"));
                task.setIsCompleted(rset.getBoolean("completed"));
                task.setDeadline(rset.getDate("deadline"));
                task.setCreatedAt(rset.getDate("createdAt"));
                task.setUpdatedAt(rset.getDate("updatedAt"));
                
                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar as tarefas do projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm, rset);
        }
        return tasks;
    }
    
}
