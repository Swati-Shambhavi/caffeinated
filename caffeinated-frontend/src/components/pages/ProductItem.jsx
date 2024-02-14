import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addToCart } from '../../store/slices/cartSlice';
import { useKeycloak } from '@react-keycloak/web';

const ProductItem = ({ product, image }) => {
  const dispatch = useDispatch();
  const {isAuthenticated} = useSelector(state => state.user)
  const { keycloak, initialized } = useKeycloak();

  const addToCartHandler = async () => {
    if(!isAuthenticated){
      console.log("User needs to be authenticated before adding anything to cart")
      keycloak.onAuthSuccess = () => {
        const userEmail = keycloak.idTokenParsed.email;
        const userData = { user: { username: keycloak.idTokenParsed.preferred_username,
        email:userEmail } };
        console.log("fromm comp",userData)
        dispatch(setUser(userData.user));
        const accessToken = keycloak.token;
        dispatch(setAccessTokenAsync(accessToken));
      };
      await keycloak.login();
    }
    console.log("Add to cart", {"productId": product.id, "quantity": 1});
    dispatch(addToCart({"productId": product.id, "quantity": 1}));
  }

  return (
    <div className='w-full sm:w-1/2 md:w-1/3 lg:w-1/4 p-4 m-2 bg-amber-800 bg-opacity-10 backdrop-filter backdrop-blur-2xl text-center text-black rounded-md shadow-md'>
      <div
        className='image-container'
        style={{
          width: '100%',
          height: '200px', // Define your desired height
          overflow: 'hidden',
          position: 'relative',
        }}
      >
        <img
          src={image}
          alt={product.name}
          className='w-full h-full object-cover rounded-md'
          style={{
            width: '100%',
            height: '100%',
            objectFit: 'cover',
            objectPosition: 'center',
          }}
        />
      </div>
      <h1 className='font-kalnia text-xl'>{product.name}</h1>
      <p className='text-sm'>{product.description}</p>
      <div className='flex font-bold justify-evenly mt-2'>
        <span>Rs. {product.price}</span>
        <span>{product.quantity} 500g</span>
        <span>Stock ({product.stockQuantity})</span>
      </div>
      <button onClick={addToCartHandler} className='w-full bg-amber-500 p-2.5 rounded-md mt-4'>
        Add to Cart
      </button>
    </div>
  );
}

export default ProductItem;
