import { combineReducers } from '@reduxjs/toolkit';
import categoryReducer from './slices/categorySlice';
import cartReducer from './slices/cartSlice';
import productReducer from './slices/productSlice';
import userReducer from './slices/userSlice';

const rootReducer = combineReducers({
  categories: categoryReducer,
  products: productReducer,
  cart: cartReducer,
  user: userReducer,
});

export default rootReducer;
