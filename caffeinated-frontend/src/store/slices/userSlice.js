import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const setAccessTokenAsync = createAsyncThunk(
  'user/setAccessToken',
  async (accessToken, { dispatch, getState }) => {
    localStorage.setItem('accessToken', accessToken);
    dispatch(setAccessToken(accessToken));

    const { email } = getState().user.userAuth;

    await dispatch(fetchUserProfileAsync({ accessToken, userEmail: email }));
  }
);

export const clearAccessTokenAsync = createAsyncThunk(
  'user/clearAccessToken',
  async (_, { dispatch }) => {
    localStorage.removeItem('accessToken');
    dispatch(clearUserAuth());
  }
);

export const authenticateUserAsync = createAsyncThunk(
  'user/authenticateUser',
  async ({ keycloak, dispatch }) => {
    try {
      if (keycloak.authenticated) {
        const { preferred_username, email } = keycloak.idTokenParsed;

        const userData = {
          userAuth: {
            username: preferred_username,
            email: email,
          },
        };

        dispatch(setUserAuth(userData.userAuth));
        await dispatch(setAccessTokenAsync(keycloak.token));
      } else {
        dispatch(clearUserAuth());
        if (!keycloak.loginCalled) {
          keycloak.onAuthSuccess = () => {
            const userEmail = keycloak.idTokenParsed.email;
            const userData = {
              userAuth: {
                username: keycloak.idTokenParsed.preferred_username,
                email: userEmail,
              },
            };
            dispatch(setUserAuth(userData.userAuth));

            const accessToken = keycloak.token;
            dispatch(setAccessTokenAsync(accessToken));
          };
          await keycloak.login();
        }
      }
    } catch (error) {
      console.error('Authentication failed:', error);
      dispatch(clearUserAuth());
    }
  }
);

export const fetchUserProfileAsync = createAsyncThunk(
  'user/fetchUserProfile',
  async ({ accessToken, userEmail }) => {
    try {
      const url = `http://localhost:8080/caffeinated/users/api/${encodeURIComponent(
        userEmail
      )}`;
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (response.status >= 200 && response.status < 300) {
        console.log('User Profile Response:', response);
        return response.data;
      } else {
        console.log('Raw response:', response);
        throw new Error('Failed to fetch user profile');
      }
    } catch (error) {
      console.error('Error fetching user profile:', error);
      throw error;
    }
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState: {
    userAuth: null,
    isAuthenticated: false,
    accessToken: null,
    userProfile: null,
  },
  reducers: {
    setUserAuth: (state, action) => {
      state.userAuth = action.payload;
      state.isAuthenticated = true;
    },
    setAccessToken: (state, action) => {
      state.accessToken = action.payload;
    },
    clearUserAuth: (state) => {
      state.userAuth = null;
      state.isAuthenticated = false;
      state.accessToken = null;
      state.userProfile = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(setAccessTokenAsync.fulfilled, (state, action) => {})
      .addCase(clearAccessTokenAsync.fulfilled, (state) => {})
      .addCase(fetchUserProfileAsync.fulfilled, (state, action) => {
        state.userProfile = action.payload.data;
        console.log('Setting user profile in state:', action.payload.data);
      })
      .addCase(fetchUserProfileAsync.rejected, (state, action) => {
        console.error('Error fetching user profile:', action.error);
      });
  },
});

export const { setUserAuth, setAccessToken, clearUserAuth } = userSlice.actions;
export default userSlice.reducer;
