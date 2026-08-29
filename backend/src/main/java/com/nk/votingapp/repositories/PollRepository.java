package com.nk.votingapp.repositories;

import com.nk.votingapp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PollRepository extends JpaRepository<Poll, Long> {
}
