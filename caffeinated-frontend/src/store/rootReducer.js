import { combineReducers } from '@reduxjs/toolkit';
import categoryReducer from './slices/categorySlice';
import cartReducer from './slices/cartSlice';
import productReducer from './slices/productSlice';

const rootReducer = combineReducers({
  categories: categoryReducer,
  product: productReducer,
  cart: cartReducer,
});

export default rootReducer;
