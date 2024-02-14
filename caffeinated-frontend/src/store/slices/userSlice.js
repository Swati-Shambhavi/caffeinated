import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

export const setAccessTokenAsync = createAsyncThunk(
  'user/setAccessToken',
  async (accessToken, { dispatch }) => {
    localStorage.setItem('accessToken', accessToken);
    dispatch(setAccessToken(accessToken));
  }
);

export const clearAccessTokenAsync = createAsyncThunk(
  'user/clearAccessToken',
  async (_, { dispatch }) => {
    localStorage.removeItem('accessToken');
    dispatch(clearUser());
  }
);
const userSlice = createSlice({
  name: 'user',
  initialState: {
    user: null,
    isAuthenticated: false,
    accessToken: null,
  },
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
      state.isAuthenticated = true;
    },
    setAccessToken: (state, action) => {
      state.accessToken = action.payload;
    },
    clearUser: (state) => {
      state.user = null;
      state.isAuthenticated = false;
      state.accessToken = null;
      state.userEmail = '';
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(setAccessTokenAsync.fulfilled, (state, action) => {})
      .addCase(clearAccessTokenAsync.fulfilled, (state) => {});
  },
});

export const { setUser, setAccessToken, clearUser } = userSlice.actions;
export default userSlice.reducer;
