package io.elliez3722.ppmtool.repositories;

import io.elliez3722.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String identifier);

    ProjectTask findByProjectSequence(String sequence);
}
