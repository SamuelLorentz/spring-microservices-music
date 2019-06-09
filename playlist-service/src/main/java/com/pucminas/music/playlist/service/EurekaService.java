package com.pucminas.music.playlist.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class EurekaService {

	@Autowired
	private DiscoveryClient discoveryClient;

	public URI getInstance(String serviceId) {

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

		if (instances != null && !instances.isEmpty()) {
			return instances.get(0).getUri();
		}

		return null;

	}

}