package com.github.anishitani.dsrouting.api;

import com.github.anishitani.dsrouting.domain.Post;
import com.github.anishitani.dsrouting.domain.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Post post) {
        Long id = service.create(post);
        return ResponseEntity.created(getEntityLocation(id.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Post fetched = service.get(id);
        return ResponseEntity.ok(fetched);
    }

    private URI getEntityLocation(String id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return uri;
    }
}
