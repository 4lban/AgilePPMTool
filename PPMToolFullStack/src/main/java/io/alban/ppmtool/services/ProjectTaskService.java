package io.alban.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alban.ppmtool.domain.ProjectTask;
import io.alban.ppmtool.repositories.BacklogRepository;
import io.alban.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask() {
		// PTs to be added to a specific project, project != null
		// set the bl to pt
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2
		// Update the BL SEQUENCE

		// INITIAL priority when priority null
		// INITIAL status when status is null

	}
}
