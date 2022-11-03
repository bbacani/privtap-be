package hr.fer.dsd.privtap.rest;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.player.PauseUsersPlaybackRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;


@RestController
@RequestMapping("/spotifyApi")
public class SpotifyActionController {

    private static final URI  redirectUri= SpotifyHttpManager.makeUri("http://localhost:8080/spotifyApi/get-user-code/");
    private String code="";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("556dda09f61f43d2ad2e425141fcfc9a")
            .setClientSecret("959d79e0d82e49e3990007f5431a70d6")
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("/spotifyLogin")
    public void spotifyAuthorization(HttpServletResponse response) throws IOException {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-modify-playback-state, user-read-playback-state")
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
       response.sendRedirect(uri.toString());
    }
    @GetMapping("/get-user-code/")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response){
        code=userCode;
        AuthorizationCodeRequest authorizationCodeRequest=spotifyApi.authorizationCode(code).build();
        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials=authorizationCodeRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        }catch(IOException | SpotifyWebApiException | ParseException e){
            System.out.println(e.getMessage());
        }
        PauseUsersPlaybackRequest request=spotifyApi.pauseUsersPlayback().build();
        try{ request.execute();}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "player paused ";
    }

}
