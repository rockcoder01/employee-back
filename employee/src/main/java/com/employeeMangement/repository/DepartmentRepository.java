package com.employeeMangement.repository;

import com.employeeMangement.enitities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
