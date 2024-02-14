import React from 'react'
import { Link } from 'react-router-dom'

const Emptycart = () => {
  return (
    <div className='text-black p-4 flex-col w-1/2 mx-auto'>
    <img src='/general/empty-cart2.svg' alt='Empty Cart' style={{ width: '200px', height: '200px' }} className='mx-auto' />
    <p className='font-serif text-lg text-center'>Your cart is empty! Go ahead and explore our delicacies.</p>
    <Link to="/">
      <button className='mt-2 bg-black text-amber-50 p-2 rounded w-full text-center mx-auto'>View products</button>
    </Link>
  </div>
  )
}

export default Emptycart