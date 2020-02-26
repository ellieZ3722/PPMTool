package io.elliez3722.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {
    private String ProjectNodeFound;

    public ProjectNotFoundExceptionResponse(String projectNodeFound) {
        ProjectNodeFound = projectNodeFound;
    }

    public String getProjectNodeFound() {
        return ProjectNodeFound;
    }

    public void setProjectNodeFound(String projectNodeFound) {
        ProjectNodeFound = projectNodeFound;
    }
}
