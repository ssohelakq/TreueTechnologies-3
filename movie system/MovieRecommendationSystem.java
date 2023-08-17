package task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MovieRecommendationSystem 
{
	    public static void main(String[] args) {
	        MovieRecommenderSystem system = new MovieRecommenderSystem();
	        system.run();
	    }
}
	class Movie {
	    private int id;
	    private String title;
	    private Set<String> genres;
	    private List<Rating> ratings;

	    public Movie(int id, String title, Set<String> genres) {
	        this.id = id;
	        this.title = title;
	        this.genres = genres;
	        this.ratings = new ArrayList<>();
	    }

	    public int getId() {
	        return id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public Set<String> getGenres() {
	        return genres;
	    }

	    public List<Rating> getRatings() {
	        return ratings;
	    }

	    public void addRating(Rating rating) {
	        ratings.add(rating);
	    }

	    public double calculateAverageRating() {
	        if (ratings.isEmpty()) {
	            return 0.0;
	        }

	        double totalRating = 0.0;
	        for (Rating rating : ratings) {
	            totalRating += rating.getScore();
	        }
	        return totalRating / ratings.size();
	    }
	}

	class User {
	    private int id;
	    private List<Rating> ratings;

	    public User(int id) {
	        this.id = id;
	        this.ratings = new ArrayList<>();
	    }

	    public int getId() {
	        return id;
	    }

	    public List<Rating> getRatings() {
	        return ratings;
	    }

	    public void addRating(Rating rating) {
	        ratings.add(rating);
	    }
	}

	class Rating {
	    private int userId;
	    private int movieId;
	    private double score;

	    public Rating(int userId, int movieId, double score) {
	        this.userId = userId;
	        this.movieId = movieId;
	        this.score = score;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public int getMovieId() {
	        return movieId;
	    }

	    public double getScore() {
	        return score;
	    }
	}

	class MovieRecommenderSystem {
	    private Map<Integer, Movie> movies = new HashMap<>();
	    private Map<Integer, User> users = new HashMap<>();
	    private Scanner scanner = new Scanner(System.in);

	    public void run() {
	        initializeMovies();
	        boolean exit = false;

	        while (!exit) {
	            System.out.println("\n1. Rate Movie\n2. Get Recommendations\n3. Exit");
	            int choice = scanner.nextInt();
	            scanner.nextLine();

	            switch (choice) {
	                case 1:
	                    rateMovie();
	                    break;
	                case 2:
	                    getRecommendations();
	                    break;
	                case 3:
	                    exit = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }

	    private void initializeMovies() {
	        movies.put(1, new Movie(1, "Movie A", new HashSet<>(Arrays.asList("Action", "Adventure"))));
	        movies.put(2, new Movie(2, "Movie B", new HashSet<>(Arrays.asList("Comedy", "Romance"))));

	    }

	    private void rateMovie() {
	        System.out.print("Enter your user ID: ");
	        int userId = scanner.nextInt();
	        scanner.nextLine();

	        if (!users.containsKey(userId)) {
	            users.put(userId, new User(userId));
	        }

	        System.out.print("Enter the movie ID you want to rate: ");
	        int movieId = scanner.nextInt();
	        scanner.nextLine();

	        if (!movies.containsKey(movieId)) {
	            System.out.println("Movie not found.");
	            return;
	        }

	        System.out.print("Enter your rating (1-10): ");
	        double score = scanner.nextDouble();
	        scanner.nextLine();
	        if (score < 1 || score > 10) {
	            System.out.println("Invalid rating. Please enter a rating between 1 and 10.");
	            return;
	        }

	        Rating rating = new Rating(userId, movieId, score);
	        users.get(userId).addRating(rating);
	        movies.get(movieId).addRating(rating);

	        System.out.println("Rating added successfully.");
	    }

	    private void getRecommendations() {
	        System.out.print("Enter your user ID: ");
	        int userId = scanner.nextInt();
	        scanner.nextLine();

	        if (!users.containsKey(userId) || users.get(userId).getRatings().isEmpty()) {
	            System.out.println("No ratings found. Cannot provide recommendations.");
	            return;
	        }

	        User user = users.get(userId);

	        List<Movie> recommendedMovies = new ArrayList<>();
	        for (Movie movie : movies.values()) {
	            boolean hasRated = user.getRatings().stream().anyMatch(r -> r.getMovieId() == movie.getId());
	            if (!hasRated) {
	                recommendedMovies.add(movie);
	            }
	        }

	        if (recommendedMovies.isEmpty()) {
	            System.out.println("No recommendations available.");
	        } else {
	            System.out.println("Recommended movies:");
	            for (Movie movie : recommendedMovies) {
	                System.out.println(movie.getTitle() + " (Avg. Rating: " + movie.calculateAverageRating() + ")");
	            }
	        }
	    }
	}

