import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';
import { json } from 'react-router-dom';

export const fetchProductItem = createAsyncThunk(
  'products/fetchProductItem',
  async (productId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/caffeinated/products/api/${productId}`
      );
      if (!response.ok) {
        let errorBody = await response.json();
        console.error(
          'Error response from server while fetching the Product:',
          errorBody
        );
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      return data;
    } catch (error) {
      throw new Error('Error fetching the Product. Please try again.');
    }
  }
);

export const addProduct = createAsyncThunk(
  'products/addProduct',
  async (product) => {
    const formData = new FormData();

    // Append the JSON data as a blob
    formData.append(
      'product',
      new Blob(
        [
          JSON.stringify({
            name: product.name,
            description: product.description,
            price: product.price,
            categoryId: product.categoryId,
            stockQuantity: product.stockQuantity,
            available: product.available,
          }),
        ],
        { type: 'application/json' }
      )
    );

    // Append the image directly
    formData.append('image', product.image);
    console.log('In async Func:', formData.values);
    try {
      const response = await axios.post(
        'http://localhost:8080/caffeinated/products/api',
        formData,
        {
          headers: {
            'Content-Type': `multipart/form-data; boundary=${formData._boundary}`,
          },
        }
      );
      console.log('AXIOS', response);
      return response.data;
    } catch (error) {
      console.error('Error adding product:', error);
      throw error;
    }
  }
);

const initialState = {
  data: {},
  error: null,
  operationStatus: 'NA',
};
const productSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProductItem.pending, (state) => {
        state.operationStatus = 'pending';
      })
      .addCase(fetchProductItem.fulfilled, (state, action) => {
        state.operationStatus = 'success';
        state.data = action.payload.data;
        state.error = null;
      })
      .addCase(fetchProductItem.rejected, (state, action) => {
        state.operationStatus = 'fail';
        state.error = action.payload.error;
      })
      .addCase(addProduct.fulfilled, (state, action) => {
        state.data.push(action.payload.data);
      });
  },
});

export default productSlice.reducer;
