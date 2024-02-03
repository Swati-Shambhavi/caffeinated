import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

export const fetchAllProducts = createAsyncThunk(
  'products/fetchAllProducts',
  async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/caffeinated/products/api'
      );
      console.log('Raw Product Response after Fetch all API call', response);
      if (response.status >= 200 && response.status < 300) {
        const data = response.data;
        console.log('JSON Product Response after Fetch all API call', data);
        return data;
      } else {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
    } catch (error) {
      console.log('Error Fetching products', error);
    }
  }
);

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
      console.log('Response', response);
      return response.data;
    } catch (error) {
      console.error('Error adding product:', error);
      throw error;
    }
  }
);

export const updateProduct = createAsyncThunk(
  'products/updateProduct',
  async ({ product, productId }) => {
    const updatedProduct = await axios.put(
      `http://localhost:8080/caffeinated/products/api/${productId}`,
      product
    );
  }
);

export const deleteProduct = createAsyncThunk(
  'products/deleteProduct',
  async ({ productId }) => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/caffeinated/products/api/${productId}`
      );
      console.log('Raw Product Response after delete API call', response);
      if (response.status >= 200 && response.status < 300) {
        const data = response.data;
        console.log('JSON Product Response after delete API call', data);
        return data;
      } else {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
    } catch (error) {
      console.log('Error deleteing product', error);
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
  reducers: {
    getProductById: (state, action) => {
      const productId = action.payload;
      const existingProduct = state.data[productId];
      if (existingProduct) {
        // Product already in state, return it
        state.operationStatus = 'success';
        state.error = null;
      } else {
        // Product not in state, trigger fetchProductItem
        fetchProductItem(state.dispatch, productId);
        state.operationStatus = 'pending';
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllProducts.pending, (state) => {
        state.operationStatus = 'pending';
      })
      .addCase(fetchAllProducts.fulfilled, (state, action) => {
        const products = action.payload.data;
        products.forEach((product) => {
          state.data[product.id] = product;
        });
        state.operationStatus = 'success';
        state.error = null;
      })
      .addCase(fetchAllProducts.rejected, (state, action) => {
        state.operationStatus = 'fail';
        state.error = action.payload.error;
      })
      .addCase(fetchProductItem.pending, (state) => {
        state.operationStatus = 'pending';
      })
      .addCase(fetchProductItem.fulfilled, (state, action) => {
        const newProduct = action.payload.data;
        state.data[newProduct.id] = newProduct;
        state.operationStatus = 'success';
        state.error = null;
      })
      .addCase(fetchProductItem.rejected, (state, action) => {
        state.operationStatus = 'fail';
        state.error = action.payload.error;
      })
      .addCase(addProduct.fulfilled, (state, action) => {
        const newProduct = action.payload.data;
        state.data[newProduct.id] = newProduct;
      });
  },
});

export default productSlice.reducer;
