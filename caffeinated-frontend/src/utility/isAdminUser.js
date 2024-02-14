import { useKeycloak } from '@react-keycloak/web';

export const isAdmin = () => {
  const { keycloak } = useKeycloak();
  const isAuthorized = () => {
    if (keycloak) {
      return (
        keycloak.hasRealmRole('ADMIN') || keycloak.hasResourceRole('ADMIN')
      );
    }
    return false;
  };
  return isAuthorized();
};
