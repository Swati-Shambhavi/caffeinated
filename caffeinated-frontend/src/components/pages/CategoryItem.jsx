import React from 'react'
import { FaLongArrowAltRight } from 'react-icons/fa'
import { Link } from 'react-router-dom'

const CategoryItem = ({ category, image }) => {
  const divStyle = {
    backgroundImage: `url(${image})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    width: '100%',
    height: '350px',
  }

  return (
    <div className='w-full md:w-1/2 lg:w-1/2 xl:w-1/2 p-8'>
      <Link to={`/categories/${category.id}/products`}>
        <div
          style={divStyle}
          className='rounded-md shadow-lg flex flex-col justify-end hover:cursor-pointer transform hover:scale-105 transition-transform duration-300 ease-in-out'
        >
          <button className='rounded-b-md flex justify-center items-center font-kalnia text-xl bg-amber-500 text-white p-2 transition-all duration-300 ease-in-out transform hover:bg-amber-600 hover:shadow-md focus:outline-none focus:ring focus:border-blue-300'>
            Explore {category.name} products{' '}
            <FaLongArrowAltRight className='ml-2' />
          </button>
        </div>
      </Link>
    </div>
  )
}

export default CategoryItem
