package io.elliez3722.ppmtool.services;

import io.elliez3722.ppmtool.domain.Project;
import io.elliez3722.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        //Logic

        return projectRepository.save(project);
    }
}
