package com.challenge.account.configuration;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@Data
@ConfigurationProperties(prefix = "challenge")
@FieldDefaults(level = PUBLIC)
public class ApplicationPropertiesConfiguration {

  String basePath;
  Postgresql postgresql;
  Integer connectTimeout;
  Integer readTimeout;
  Service services;
  DataSize maxInMemorySize;


  @Data
  @FieldDefaults(level = PUBLIC)
  public static class Postgresql {
    String host;
    Integer port;
    String dbname;
    String schema;
    String username;
    String password;
    String sslMode;
  }

  @Data
  @FieldDefaults(level = PRIVATE)
  public static class Service {
    Customer customer;
  }

  @Data
  @FieldDefaults(level = PRIVATE)
  public static class Customer {

    String baseUrl;
    String getIdentificationPath;
  }
}
