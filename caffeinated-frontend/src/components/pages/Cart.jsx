import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {
  addToCart,
  fetchCartDetails,
  removeFromCart,
} from '../../store/slices/cartSlice'
import CartDetail from './CartDetail'
import axios from 'axios'
import { sendPaymentConfirmation } from '../../store/slices/orderSlice'
import { useNavigate } from 'react-router'
import DisplayEmpty from './Empty'

const Cart = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { data, operationStatus, error } = useSelector((state) => state.cart)
  const { userProfile } = useSelector((state) => state.user)
  useEffect(() => {
    dispatch(fetchCartDetails())
  }, [dispatch])

  const onAddCartItem = (productId) => {
    console.log('Add to cart', { productId: productId, quantity: 1 })
    dispatch(addToCart({ productId: productId, quantity: 1 }))
  }

  const onRemoveCartItem = (productId) => {
    console.log(productId, 'removed')
    dispatch(removeFromCart({ productId: productId }))
  }

  const onPlaceOrder = async (totalAmount) => {
    console.log('Initiated order for amount Rs.', totalAmount)
    try {
      const response = await axios.post(
        'http://localhost:8080/caffeinated/orders/api/create-order',
        {
          amount: totalAmount * 100,
          currency: 'INR',
        }
      )
      console.log('reasponse from order:', response)
      const order = response.data
      console.log('order', order)
      const options = {
        key: order.data.apiKey,
        amount: order.data.amount,
        currency: order.data.currency,
        name: 'Caffeinated',
        description: 'Order placed for caffeinated products',
        order_id: order.data.orderId,
        handler: function (response) {
          console.log('Response after successful payment', response)
          dispatch(sendPaymentConfirmation(response))
          navigate('/user/order')
        },
      }
      const razorpayInstance = new Razorpay(options)
      razorpayInstance.open()
    } catch (error) {
      console.error('Error placing order:', error)
    }
  }

  return (
    <div className='mt-24'>
      {data && data.cartItems && data.cartItems.length == 0 && (
        <DisplayEmpty displayText='Your cart is empty' />
      )}
      {data && data.cartItems && data.cartItems.length !== 0 && (
        <CartDetail
          cart={data}
          onAdd={onAddCartItem}
          onRemove={onRemoveCartItem}
          user={userProfile}
          onPlaceOrder={onPlaceOrder}
        />
      )}
    </div>
  )
}

export default Cart
