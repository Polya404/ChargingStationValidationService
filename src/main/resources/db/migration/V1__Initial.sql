CREATE TABLE charging_station (
                                  id VARCHAR(255) NOT NULL,
                                  title VARCHAR(255),
                                  description VARCHAR(255),
                                  address VARCHAR(255),
                                  geo_coordinates VARCHAR(255),
                                  is_public BOOLEAN NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE connector (
                           id VARCHAR(255) NOT NULL,
                           type VARCHAR(255) NOT NULL,
                           max_power_kw INT NOT NULL,
                           charging_station_id VARCHAR(255) NOT NULL ,
                           PRIMARY KEY (id),
                           FOREIGN KEY (charging_station_id) REFERENCES charging_station(id)
);

CREATE TABLE response (
                          id UUID NOT NULL,
                          id_station VARCHAR(255) NOT NULL,
                          message TEXT,
                          PRIMARY KEY (id)
);

CREATE TABLE response_id_connectors (
                                        response_id UUID NOT NULL,
                                        id_connectors VARCHAR(255) NOT NULL,
                                        PRIMARY KEY (response_id, id_connectors),
                                        FOREIGN KEY (response_id) REFERENCES response(id),
                                        FOREIGN KEY (id_connectors) REFERENCES connector(id)
);



