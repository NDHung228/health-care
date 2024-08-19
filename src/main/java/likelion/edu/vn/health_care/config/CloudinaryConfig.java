package likelion.edu.vn.health_care.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "djm4rfbne";
    private final String API_KEY = "564838586672682";
    private final String API_SECRET = "K4tqFiNW_107ytQmm1KoZLmDDjQ";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);

        return new Cloudinary(config);
    }
}