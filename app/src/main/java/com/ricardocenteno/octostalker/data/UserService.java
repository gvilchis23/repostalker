package com.ricardocenteno.octostalker.data;
import io.reactivex.Observable;
import com.ricardocenteno.octostalker.model.User;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("/orgs/{id}/members")
    Observable<List<User>> fetchUsers(@Path("id") String organization);

    @GET("/users/{id}")
    Observable<User> getUser(@Path("id") String user);

    @GET("/users/{id}/following")
    Observable<List<User>> getFollowingUser(@Path("id") String user);

}
