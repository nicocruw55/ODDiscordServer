package com.odfin.persistence.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Group {
    private Long groupId;
    private String groupName;
    private User createdBy;
    private LocalDateTime createdAt;
    private List<User> members;

    public Group(Long groupId, String groupName, User createdBy, LocalDateTime createdAt, List<User> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.members = members;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", members=" + members +
                '}';
    }
}
