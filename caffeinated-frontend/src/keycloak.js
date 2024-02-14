import Keycloak from 'keycloak-js';

const keycloakConfig = {
  realm: 'dev',
  clientId: '',
  url: 'http://localhost:8060/auth/',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;
