package overridetech.task231.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import overridetech.task231.configuration.AppConfiguration;
import overridetech.task231.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DaDataService {
    @Autowired
    private UserRepository userRepository;

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private AppConfiguration appConfiguration;

    private double radius_meters = 1000.0;
    private String urlForCoordinates = "https://cleaner.dadata.ru/api/v1/clean/address";
    private String urlForPostAddress = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/geolocate/postal_unit";
    private String acceptValue = "application/json; charset=UTF-8";

    public JSONArray getNearestPostOffices(Long userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", appConfiguration.getAPIKey());
        headers.set("X-Secret", appConfiguration.getSecretKey());
        headers.set("Accept", acceptValue);

        String body = userRepository.findById(userId).get().getAddress();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(body);

        HttpEntity<JSONArray> requestForCoordinates = new HttpEntity<>(jsonArray, headers);
        ResponseEntity<JSONArray> responseForCoordinates = restTemplate().postForEntity(urlForCoordinates, requestForCoordinates, JSONArray.class);

        Map<String, Object> jsonMap = (Map<String, Object>) responseForCoordinates.getBody().get(0);
        String geoLat = jsonMap.get("geo_lat").toString();
        String geoLon = jsonMap.get("geo_lon").toString();

        Map<String, Double> geoMap = new HashMap<>();
        geoMap.put("lat", Double.parseDouble(geoLat));
        geoMap.put("lon", Double.parseDouble(geoLon));
        geoMap.put("radius_meters", radius_meters);

        HttpEntity<Map<String, Double>> requestForPostAddr = new HttpEntity(geoMap, headers);
        ResponseEntity<JSONObject> responseForPostAddr = restTemplate().postForEntity(urlForPostAddress, requestForPostAddr, JSONObject.class);
        ArrayList<Object> overallPostOfficeInfo = (ArrayList<Object>) responseForPostAddr.getBody().get("suggestions");
        Map<String, Object> singlePostOfficeInfo = new HashMap<>();
        JSONArray essentialPostOfficeInfo = new JSONArray();
        for (Object o : overallPostOfficeInfo) {
            singlePostOfficeInfo = (Map<String, Object>) ((Map<String, Object>) o).get("data");
            essentialPostOfficeInfo.add(singlePostOfficeInfo);
        }

        return essentialPostOfficeInfo;
    }
}
