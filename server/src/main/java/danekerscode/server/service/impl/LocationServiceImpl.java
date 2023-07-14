package danekerscode.server.service.impl;

import danekerscode.server.service.LocationService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    @Value("${location.longitude}")
    private Integer longitude;

    @Value("${location.latitude}")
    private Integer latitude;

    @Override
    public boolean validate(Integer longitude, Integer latitude) {
        return this.latitude.equals(latitude)
                && this.longitude.equals(longitude);
    }
}
