package ca.vijaysharma.resume.network;

import java.util.Map;

import retrofit.http.GET;
import rx.Observable;

public interface ResumeService {

    @GET("/vijaysharm/resume-web/master/resume.json")
    Observable<Map<String, Object>> resume();
}
