package com.yurwar.trainingcourse.controller.dto;

import com.yurwar.trainingcourse.model.entity.ActivityImportance;

public class ActivityDTO {
    private String name;
    private String description;
    private ActivityImportance importance;

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityImportance getImportance() {
        return importance;
    }

    public void setImportance(ActivityImportance importance) {
        this.importance = importance;
    }

    public static class Builder {
        private String name;
        private String description;
        private ActivityImportance importance;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder importance(ActivityImportance importance) {
            this.importance = importance;
            return this;
        }

        public ActivityDTO build() {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setName(name);
            activityDTO.setDescription(description);
            activityDTO.setImportance(importance);
            return activityDTO;
        }
    }
}
