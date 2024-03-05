import React, { useState } from 'react'
import { FaAngleDown, FaAngleUp } from 'react-icons/fa'

const DisplayCustomizedProduct = ({
  customizedProduct,
  unhideForm,
  extraProductInformation,
}) => {
  const [showMoreInfo, setShowMoreInfo] = useState(false)

  return (
    <div className='border-gray-200 border shadow-md px-4 py-2'>
      <h1 className='text-lg font-semibold font-kalnia'>
        {customizedProduct.productName}
      </h1>
      <p className='text-gray-700 mb-2'>
        {customizedProduct.productDescription}
      </p>
      <p className='text-gray-800'>
        <span className='font-semibold'>Caffeine content: </span>
        <span className='text-sm'>{customizedProduct.caffeineContent}</span>
      </p>
      <div className='flex justify-end my-2'>
        <button className='bg-black p-2 text-amber-50 mr-2 rounded-sm'>
          Add to Cart
        </button>
        <button
          className='bg-black p-2 text-amber-50 rounded-sm'
          onClick={() => unhideForm()}
        >
          Customize Product
        </button>
      </div>
      <div
        className={`flex justify-between text-gray-600 ${
          showMoreInfo ? 'border-none' : 'border shadow-md my-4'
        } p-2 hover:cursor-pointer transition-all`}
        onClick={() => setShowMoreInfo((prevState) => !prevState)}
        style={{ maxHeight: showMoreInfo ? '1000px' : '50px' }}
      >
        <span>
          {showMoreInfo ? 'Hide extra information' : 'Show more information'}
        </span>
        <span>{showMoreInfo ? <FaAngleUp /> : <FaAngleDown />}</span>
      </div>
      {showMoreInfo && (
        <div className='flex px-4 py-2 mt-2 overflow-y-auto'>
          <ul className='w-1/2'>
            <span className='font-semibold'>Ingredients:</span>
            {customizedProduct.ingredients.map((ingredient, index) => (
              <li
                key={index}
                className='border-b border-gray-200 w-1/2 font-light'
              >
                {ingredient}
              </li>
            ))}
          </ul>
          {extraProductInformation.dietaryRestrictions.length > 0 && (
            <ul className='w-1/2'>
              <span className='font-semibold'>Dietary Restrictions:</span>
              {extraProductInformation.dietaryRestrictions.map(
                (dietaryRestriction, index) => (
                  <li
                    key={index}
                    className='border-b border-gray-200 w-1/2 font-light'
                  >
                    {dietaryRestriction}
                  </li>
                )
              )}
            </ul>
          )}
        </div>
      )}
    </div>
  )
}

export default DisplayCustomizedProduct
