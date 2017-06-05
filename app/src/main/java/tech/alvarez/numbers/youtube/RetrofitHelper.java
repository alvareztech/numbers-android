package tech.alvarez.numbers.youtube;

public class RetrofitHelper {

    public static YouTubeDataApiService getYouTubeService() {
        return RetrofitClient.getClient().create(YouTubeDataApiService.class);
    }

}
