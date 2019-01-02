package com.vervloet.msgservices.domain.vo.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;

/**
 * The Base class for JSONApi models, defines the ID To be used in the JSONAPI Data Class.
 */
public abstract class BaseJsonApiModel {

  public static final String BASE_PATH = "/api";

  @JsonIgnore
  private Long id;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the JSON-API resource type.
   *
   * @return JSON-API resource's name
   */
  @JsonIgnore
  public abstract String getTypeName();

  /**
   * Gets the base Url for the resource, used for creating the "Self" reference in the link.
   *
   * @return base url
   */
  @JsonIgnore
  public abstract Optional<String> baseUrl();

}
