package io.elliez3722.ppmtool.services;

import io.elliez3722.ppmtool.domain.Backlog;
import io.elliez3722.ppmtool.domain.Project;
import io.elliez3722.ppmtool.domain.ProjectTask;
import io.elliez3722.ppmtool.exceptions.ProjectNotFoundException;
import io.elliez3722.ppmtool.repositories.BacklogRepository;
import io.elliez3722.ppmtool.repositories.ProjectRepository;
import io.elliez3722.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PTs to be added to a specific project, project != null, backlog exists
            projectIdentifier = projectIdentifier.toUpperCase();
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like XXXX-1, XXXX-2
            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            //add sequence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }
            //INITIAL status when status null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found.");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        id = id.toUpperCase();

        Project project = projectRepository.findByProjectIdentifier(id);
        if (project == null) {
            throw new ProjectNotFoundException("Project with id '" + id + "' is not found.");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findByProjectSequence(String backlog_id, String pt_id) {
        backlog_id = backlog_id.toUpperCase();
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project with id '" + backlog_id + "' is not found.");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task with id '" + pt_id + "' is not found.");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' does not exist in Project '" + backlog_id + "'.");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);
    }
}
