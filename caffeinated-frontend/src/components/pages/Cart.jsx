import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addToCart, fetchCartDetails, removeFromCart } from '../../store/slices/cartSlice';
import Emptycart from './Emptycart';
import CartDetail from './CartDetail';

const Cart = () => {
  const dispatch = useDispatch();
  const { data, operationStatus, error } = useSelector(state => state.cart);
  const {userProfile} = useSelector(state=>state.user)
  useEffect(() => {
    dispatch(fetchCartDetails());
  }, [dispatch]);

  const onAddCartItem = (productId) =>{
    console.log("Add to cart", {"productId":productId, "quantity":1})
    dispatch(addToCart({"productId":productId, "quantity":1}))
  }

  const onRemoveCartItem = (productId) =>{
    console.log(productId,'removed')
    dispatch(removeFromCart({"productId":productId}))
  }

  return (
    <div className='mt-24'>
      {data && data.cartItems &&data.cartItems.length == 0 && (
        <Emptycart/>       
      )}
      {data && data.cartItems && data.cartItems.length !== 0 && (
        <CartDetail cart={data} onAdd={onAddCartItem} onRemove={onRemoveCartItem} user={userProfile}/>
       
      )}
    </div>
  );
};

export default Cart;
