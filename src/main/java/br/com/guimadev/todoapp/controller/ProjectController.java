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
import br.com.guimadev.todoapp.util.ConnectionFactory;

/**
 *
 * @author Luis Felipe Guimarães Pinto
 */
public class ProjectController {
    
    public Project save(Project project) {
        
        String query = "INSERT INTO projects (name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        List<Project> projects;
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, project.getName());
            pstm.setString(2, project.getDescription());
            pstm.setDate(3, new Date(project.getCreatedAt().getTime()));
            pstm.setDate(4, new Date(project.getUpdatedAt().getTime()));
            pstm.execute();
            
            projects = this.getAll();
            return projects.get(projects.size() - 1);
            
        } catch (SQLException ex) {
            throw new RuntimeException("Não foi possivel cadastrar o projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
        }
        
    }
    
    public void update(Project project) {
        
        String query = "UPDATE projects SET name = ?, description = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, project.getName());
            pstm.setString(2, project.getDescription()); 
            pstm.setDate(3, new Date(project.getCreatedAt().getTime()));
            pstm.setDate(4, new Date(project.getUpdatedAt().getTime()));
            pstm.setInt(5, project.getId());
            pstm.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar um projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
        } 
        
    }
    
    public void removeById(int projectId) {
        
        String query = "DELETE FROM projects WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, projectId);
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar um projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm);
        }
        
    }
    
    public void removeAll() {
        
        List<Project> projects = getAll();
        projects.forEach(p -> removeById(p.getId()));
        
    }
    
    public List<Project> getAll() {
            
        String query = "SELECT * FROM projects";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        
        List<Project> projects = new ArrayList<>();
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(query);
            rset = pstm.executeQuery();
            
            while (rset.next()) {
                Project project = new Project();
                project.setId(rset.getInt("id"));
                project.setName(rset.getString("name"));
                project.setDescription(rset.getString("description"));
                project.setCreatedAt(rset.getDate("createdAt"));
                project.setUpdatedAt(rset.getDate("updatedAt"));
                
                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar os projetos " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conn, pstm, rset);
        }
        
        return projects;
    }
    
}
