package com.company.jmixpm.entity;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.validation.ProjectLabelsSize;
import com.company.jmixpm.validation.ValidDatesProject;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.*;
import io.jmix.core.pessimisticlocking.PessimisticLock;
import io.jmix.core.validation.group.UiCrossFieldChecks;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@PessimisticLock(timeoutSec = 10)
@JmixEntity
@Table(name = "PROJECT", indexes = {
        @Index(name = "IDX_PROJECT_MANAGER", columnList = "MANAGER_ID")
})
@Entity
@ValidDatesProject(groups = {UiCrossFieldChecks.class})
public class Project {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @NotNull
    @JoinColumn(name = "MANAGER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User manager;

    @Column(name = "STATUS")
    private Integer status;

    @JoinTable(name = "PROJECT_USER_LINK",
            joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<User> participants;

    @Composition
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    //@Convert(converter = ProjectLabelsConverter.class)
    @Column(name = "PROJECT_LABELS")
    @ProjectLabelsSize(min = 2, max = 5)
    private ProjectLabels projectLabels;

    @Column(name = "TOTAL_ESTIMATED_EFFORTS")
    private Integer totalEstimatedEfforts;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @SystemLevel
    @Column(name = "OWNER_ID")
    private UUID ownerId;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @DependsOnProperties({"ownerId"})
    @JmixProperty
    @Transient
    private Customer owner;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTotalEstimatedEfforts() {
        return totalEstimatedEfforts;
    }

    public void setTotalEstimatedEfforts(Integer totalEstimatedEfforts) {
        this.totalEstimatedEfforts = totalEstimatedEfforts;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public ProjectLabels getProjectLabels() {
        return projectLabels;
    }

    public void setProjectLabels(ProjectLabels projectLabels) {
        this.projectLabels = projectLabels;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public ProjectStatus getStatus() {
        return status == null ? null : ProjectStatus.fromId(status);
    }

    public void setStatus(ProjectStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @PostConstruct
    public void postConstruct() {

        setStartDate(LocalDateTime.now());

    }
}