import React, { useState } from 'react'
import CartItem from './CartItem'
import OrderForm from './OrderForm'

const CartDetail = ({cart, onAdd, onRemove, user}) => {
  const [showOrderForm, setShowOrderForm] = useState(false)
  return (
    <div className='flex flex-col md:flex-row px-8 justify-between'>
        <div className=' w-full md:w-1/2'>
            {showOrderForm ? <OrderForm user={user} setShowOrderForm={setShowOrderForm}/> : 
              <div className='max-w-md mx-auto mt-8 p-4 bg-amber-800 bg-opacity-10 backdrop-filter backdrop-blur-2xl rounded'>
                <section className='flex'>
                  <span className='flex-col justify-between'>
                  <p>Deliver To:</p>
                  <h1>{user.name}, {user.address.pinCode}</h1>
                  <p>{user.address.address1}, {user.address.city}, {user.address.state}</p>
                </span>
                <button className='font-bold hover:cursor-pointer' onClick={()=>setShowOrderForm(prevstate=>!prevstate.showOrderForm)}>
                    {user.address.city? 'Change' : 'Add your address'}
                    </button>
                </section>            
                
                </div>}
        </div>
        <div className='w-full md:w-1/2'>
            <h1 className='font-serif text-center text-2xl'>Your Cart details:</h1>
            {cart.cartItems.map(cartItem => (
            <CartItem key={cartItem.id} cartItem={cartItem} onAdd = {onAdd} onRemove = {onRemove} />
            ))}
            <div className='flex-col mt-2'>
            <h1 className='font-serif pl-4 text-lg'>Your total Amount: {cart.totalPrice}</h1>
            <button className='p-2 m-2 bg-black text-amber-50 w-2/3 rounded'>Place your Order</button>
            </div>
        </div>
  </div>
  )
}

export default CartDetail