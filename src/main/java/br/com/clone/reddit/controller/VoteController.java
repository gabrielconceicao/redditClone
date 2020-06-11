package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.VoteDto;
import br.com.clone.reddit.service.VoteService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(value = "Vote")
@RequestMapping("/api/votes/")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
