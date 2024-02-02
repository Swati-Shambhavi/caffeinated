import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchCartDetails } from '../store/slices/cartSlice';

const Cart = () => {
    const dispatch = useDispatch();
    const {data, operationStatus, error} = useSelector(state => state.cart)
    useEffect(()=>{
        useDispatch(fetchCartDetails())
    },[dispatch])
  return (
    <div>Cart</div>
  )
}

export default Cart