package com.example.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.cloud.entity.Movie;
import com.example.cloud.entity.Review;
import com.example.cloud.service.MovieService;

import java.util.List;

@Controller
@RequestMapping("/movies")  // 이 부분을 수정했습니다.
public class MovieController {

    @Autowired
    private MovieService movieService;

    // 모든 영화 목록을 가져오는 API
    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);  // "movies"라는 이름으로 리스트 전달
        return "movie"; // templates/movie.html로 이동
    }

    // 특정 영화의 상세 정보를 가져오는 API
    @GetMapping("/{movieId}")  // URL 경로를 "/movies/{movieId}"로 수정
    public String getMovieDetails(@PathVariable String movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "movieDetails"; // movie-details.html로 이동
    }

    // 영화에 대한 리뷰를 추가하는 API
    @PostMapping("/{movieId}/review")  // URL 경로를 "/movies/{movieId}/review"로 수정
    public String addReview(@PathVariable String movieId, @RequestParam int rating, @RequestParam String content) {
        // 영화 ID로 영화 정보를 가져옵니다.
        Movie movie = movieService.getMovieById(movieId);

        // 리뷰 객체 생성 및 설정
        Review review = new Review();
        review.setRating(rating);
        review.setContent(content);
        review.setMovie(movie);

        // 영화에 리뷰를 추가
        movieService.addReviewToMovie(movieId, review);

        // 리뷰 추가 후 해당 영화의 상세 페이지로 리디렉션
        return "redirect:/movies/" + movieId;
    }
}