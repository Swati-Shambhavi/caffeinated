import React from 'react'
import { FaArrowRight } from 'react-icons/fa'
import { Link } from 'react-router-dom'

const CustomizeYourProduct = () => {
  return (
    <div className='font-bold text-lg text-black  w-full m-8 border-t border-b border-gray-300 md:flex-row flex'>
      <div className='hidden sm:block h-96 w-96 p-3' style={{ height: 'auto' }}>
        <img
          src='/general/customize-product.svg'
          alt='Coffee Image'
          className='object-contain h-full w-full'
        />
      </div>
      <div className='flex py-3 md:p-8 md:px-20'>
        <div className='flex space-x-2'>
          <h3
            style={{
              background:
                'linear-gradient(45deg, #FFD700, #FF4500, #FFD700, #FF4500)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              display: 'inline-block',
              animation: 'pulse 2s infinite, shine 2s infinite linear',
              textShadow:
                '0 0 10px rgba(255, 255, 255, 0.8), 0 0 20px rgba(255, 255, 255, 0.8)',
              backgroundSize: '200% 100%',
            }}
          >
            New
          </h3>
          <div className='flex flex-col' style={{ height: 'auto' }}>
            <h2 className='text-l font-bold font-kalnia text-gray-800'>
              Customize Your Product
            </h2>
            <p className='text-sm  text-gray-600 font-normal font-serif'>
              Exciting new feature! Customize your caffeinated product with our
              personalized options. Choose your blend, adjust caffeine levels,
              and pick your flavor.{' '}
              <span className='font-semibold'>
                Create a unique coffee experience just for you!
              </span>
            </p>
            <Link
              to='/products/customize'
              className='flashy-amber-button flex justify-center items-center h-10 w-1/2 text-white self-end mt-6 '
            >
              Customize your product
              <FaArrowRight className='ml-2' />
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CustomizeYourProduct
