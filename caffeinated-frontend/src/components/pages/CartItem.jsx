import React, { useState } from 'react'
import { FaPlusCircle, FaMinusCircle } from 'react-icons/fa';

const CartItem = ({cartItem, onAdd, onRemove}) => {
  const [openForm, setOpenForm] = useState(false)
  return (
    <div className='flex-col border-b p-4 justify-items-center'>
        <p className='font-serif'>{cartItem.product.name}</p>
        <div className='flex justify-between'>
          <span>Price: {cartItem.unitPrice}</span>
          <div className='flex justify-center items-center'>
            <button onClick={()=>onRemove(cartItem.product.id)} className='hover:cursor-pointer '><FaMinusCircle/></button>
            <p className='ml-3 mr-3 font-bold'>{cartItem.quantity}</p>
            <button onClick={()=>onAdd(cartItem.product.id)} className='hover:cursor-pointer'><FaPlusCircle/></button>
        </div>
        </div>
    </div>
  )
}

export default CartItem