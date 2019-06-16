package io.alban.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alban.ppmtool.domain.Backlog;
import io.alban.ppmtool.domain.Project;
import io.alban.ppmtool.domain.ProjectTask;
import io.alban.ppmtool.exceptions.ProjectNotFoundException;
import io.alban.ppmtool.repositories.BacklogRepository;
import io.alban.ppmtool.repositories.ProjectRepository;
import io.alban.ppmtool.repositories.ProjectTaskRepository;

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
			// PTs to be added to a specific project, project != null
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			// set the bl to pt
			projectTask.setBacklog(backlog);
			// we want our project sequence to be like this: IDPRO-1 IDPRO-2
			Integer BacklogSequence = backlog.getPTSequence();
			// Update the BL SEQUENCE
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);

			// Add Sequence to PT
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			// INITIAL priority when priority null
			if (projectTask.getPriority() == null) {
				// In the future we need projectTask.getPriority()==0 to handle the form
				projectTask.setPriority(3);
			}

			// INITIAL status when status is null
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project not found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepository.findByProjectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Doesn't exist any backlog/task with project ID: '" + id + "'");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
}
