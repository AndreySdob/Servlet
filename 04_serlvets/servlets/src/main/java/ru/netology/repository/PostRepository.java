package ru.netology.repository;

import ru.netology.model.Post;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong counter = new AtomicLong(1);

  public List<Post> all() { return new ArrayList<>(posts.values()); }

  public Optional<Post> getById(long id) { return Optional.ofNullable(posts.get(id)); }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = counter.getAndIncrement();
      post.setId(newId);
      posts.put(newId, post);
      return post;
    }
    return posts.computeIfPresent(post.getId(), (id, oldPost) -> {
      oldPost.setContent(post.getContent());
      return oldPost;
    });
  }

  public void removeById(long id) { posts.remove(id); }
}
