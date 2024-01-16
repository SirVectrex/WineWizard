package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepositoryI extends CrudRepository<Feedback, Long> {

}
