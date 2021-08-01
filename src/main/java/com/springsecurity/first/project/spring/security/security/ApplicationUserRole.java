package com.springsecurity.first.project.spring.security.security;

import com.google.common.collect.Sets;
import static com.springsecurity.first.project.spring.security.security.ApplicationUserPermission.*;

import java.util.Set;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_WRITE, COURSE_READ, STUDENT_WRITE, STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions()
    {
        return this.permissions;
    }
}
