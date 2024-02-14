import React, { useEffect, useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setUser, setAccessTokenAsync, clearUser } from '../store/slices/userSlice';
import { useKeycloak } from '@react-keycloak/web';

const AuthWrapper = () => {
  const { keycloak, initialized } = useKeycloak();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(true);
  const { isAuthenticated } = useSelector(state => state.user);

  useEffect(() => {
    const initKeycloak = async () => {
      try {
        if (initialized && keycloak.authenticated) {
          const userEmail = keycloak.idTokenParsed.email;
          const userData = { user: { username: keycloak.idTokenParsed.preferred_username,
          email: userEmail } };
          console.log("fromm comp",userData)
          dispatch(setUser(userData.user));

          const accessToken = keycloak.token;
          await dispatch(setAccessTokenAsync(accessToken)); 

        } else {
          dispatch(clearUser());
          if (!keycloak.loginCalled) {
            keycloak.onAuthSuccess = () => {
              const userEmail = keycloak.idTokenParsed.email;
              const userData = { user: { username: keycloak.idTokenParsed.preferred_username,
              email:userEmail } };
              console.log("fromm comp",userData)
              dispatch(setUser(userData.user));

              const accessToken = keycloak.token;
              dispatch(setAccessTokenAsync(accessToken));
            };
            keycloak.login();
          }
        }
      } catch (error) {
        console.error('Keycloak initialization failed:', error);
        dispatch(clearUser());
        navigate('/');
      } finally {
        setLoading(false);
      }
    };

    if (initialized) {
      initKeycloak();
    }
  }, [dispatch, navigate, keycloak, initialized]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return isAuthenticated ? <Outlet/> : null;
};

export default AuthWrapper;