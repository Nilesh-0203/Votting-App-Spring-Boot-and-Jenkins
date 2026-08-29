package com.nk.votingapp.services;

import com.nk.votingapp.model.OptionVote;
import com.nk.votingapp.model.Poll;
import com.nk.votingapp.repositories.PollRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;
    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(@RequestBody Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionIndex) {
        //Get Poll from DB
        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new RuntimeException("Poll not found"));

        //Get All Options from poll
        List<OptionVote>  options = poll.getOptions();

        if(optionIndex<0 || optionIndex>=options.size()){
            throw new IllegalArgumentException("Invalid option index");
        }

        OptionVote selectedOption = options.get(optionIndex);

        selectedOption.setVoteCount(selectedOption.getVoteCount()+1);

        pollRepository.save(poll);

    }

}
