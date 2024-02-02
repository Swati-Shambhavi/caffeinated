import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

// Async Thunk to fetch categories
export const fetchCartDetails = createAsyncThunk(
  'cart/fetchCartDetails',
  async () => {
    try {
      const response = await fetch(
        'http://localhost:8080/caffeinated/carts/api'
      );
      if (!response.ok) {
        let errorBody;
        try {
          errorBody = await response.clone().json();
        } catch (jsonError) {
          errorBody = await response.text();
        }

        console.error('Error response from server:', errorBody);
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching categories:', error);
      throw new Error('Error fetching categories. Please try again.');
    }
  }
);

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    data: {
      id: '',
      cartItems: [],
      totalPrice: '',
      user: '',
    },
    operationStatus: '',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCartDetails.pending, (state) => {
        state.operationStatus = 'pending';
      })
      .addCase(fetchCartDetails.rejected, (state, action) => {
        state.operationStatus = 'rejected';
        state.error = action.error.message;
      })
      .addCase(fetchCartDetails.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled';
        state.data = action.payload;
      });
  },
});
export default cartSlice.reducer;
