import React, { useState } from 'react'
import CustomizationProductForm from './CustomizeProductForm'

const CustomizeProduct = () => {
  return (
    <div className='mt-16'>
      <div className='text-center mb-2'>
        <h1 className='text-2xl font-kalnia text-pink-600 font-semibold'>
          Get your product customized!
        </h1>
        <p className='mb-0'>
          Be the creator of your caffeinated goodness by just answering a few
          questions.
        </p>
      </div>
      <div className='flex -mt-2'>
        <div className='sm:w-1/5 hidden sm:block'></div>
        <div className='sm:w-3/5 w-full p-6'>
          <CustomizationProductForm />
        </div>
        <div className='sm:w-1/5 hidden sm:block'></div>
      </div>
    </div>
  )
}

export default CustomizeProduct
