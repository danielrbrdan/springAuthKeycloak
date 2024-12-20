package com.project.base.app.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.base.app.test.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
