package com.example.back_end.repository;

import com.example.back_end.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByVideoUrl(String videoUrl);

}
