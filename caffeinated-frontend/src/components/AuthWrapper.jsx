// AuthWrapper.js
import React, { useEffect } from 'react';
import { useNavigate, Routes, Route } from 'react-router-dom';
import keycloak from '../config/keycloak';
import { useDispatch } from 'react-redux';
import { setUser, setAccessToken, clearUser } from './userSlice';

const AuthWrapper = ({ children }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    const initKeycloak = async () => {
      try {
        await keycloak.init({ onLoad: 'login-required' });

        if (keycloak.authenticated) {
          const userData = { user: { username: keycloak.idTokenParsed.preferred_username } };
          dispatch(setUser(userData.user));
          dispatch(setAccessToken(keycloak.token));
        }
      } catch (error) {
        console.error('Keycloak initialization failed:', error);
        dispatch(clearUser());
        navigate('/');
      }
    };

    initKeycloak();
  }, [dispatch, navigate]);

  if (keycloak.authenticated) {
    return <>{children}</>;
  } else {
    navigate('/');
    return null;
  }
};

export default AuthWrapper;
