package hr.fer.dsd.privtap.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.fer.dsd.privtap.model.user.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "serviceProviders")
public class ServiceProviderEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private Boolean emailVerified = false;
    private String imageUrl;
    @JsonIgnore
    private String password = null;
    private AuthProvider provider;
    private String providerId;
    private String platformId;
}