//import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.CropImageFilter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherNetworkingClient {
    private String baseURL;
    private String apiKey;

    public WeatherNetworkingClient()
    {
        baseURL = "http://api.weatherapi.com/v1";
        apiKey = "a4edf00758e34343874165914221605";
    }

    public Weather getCurrentForecast(String zip)
    {
        String url = makeAPICall(zip);
        Weather forecast = parseForecast(url);
        return forecast;
    }

    public String makeAPICall(String zipCode) {
        String endPoint = "/current.json";
        String url = baseURL + endPoint + "?key=" + apiKey + "&q=" + zipCode;

        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Weather parseForecast(String json)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONObject currentObj = jsonObj.getJSONObject("current");
        double currentTempF = currentObj.getDouble("temp_f");
        double currentTempC = currentObj.getDouble("temp_c");
        JSONObject conditionObj = currentObj.getJSONObject("condition");
        String currentCond = conditionObj.getString("text");
        String currentCondIcon = conditionObj.getString("icon");
        Weather forecast = new Weather(currentTempF, currentTempC, currentCond, currentCondIcon);
        return forecast;
    }


}
