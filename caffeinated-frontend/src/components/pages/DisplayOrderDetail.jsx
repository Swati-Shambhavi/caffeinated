import React, { useState } from 'react'

const DisplayOrderDetail = ({ order }) => {
  const [isOrderItemsVisible, setIsOrderItemsVisible] = useState(false)

  const toggleOrderItemsVisibility = () => {
    setIsOrderItemsVisible(!isOrderItemsVisible)
  }

  return (
    <>
      <div className='mt-5 p-4 bg-amber-800 bg-opacity-10 backdrop-filter backdrop-blur-2xl rounded ml-20 mr-20'>
        <div className='flex justify-between items-center mb-1.5'>
          <h3 className='text-xl font-semibold'>Order ID: {order.orderId}</h3>
          <p className='text-gray-600'>
            Ordered On: {new Date(order.orderedOn).toLocaleString()}
          </p>
        </div>

        <div className='flex items-center justify-between mb-2'>
          <p className=' font-semibold'>
            Total Price: Rs.{order.totalPrice.toFixed(2)}
          </p>
          <button
            onClick={toggleOrderItemsVisibility}
            className='px-4 py-2 text-amber-50 bg-black rounded-full hover:bg-slate-950 focus:outline-none'
          >
            {isOrderItemsVisible ? 'Hide Items' : 'Show Items'}
          </button>
        </div>

        {isOrderItemsVisible && (
          <div className='mt-4'>
            <h4 className='text-lg font-semibold mb-2'>Order Items:</h4>
            <ul className='list-disc pl-6'>
              {order.orderItems.map((item) => (
                <li key={item.productId} className='mb-4'>
                  <div className='font-semibold text-base'>
                    {item.productName}
                  </div>
                  <div className='text-gray-600'>
                    Quantity: {item.productQuantity}
                  </div>
                  <div className='text-gray-600'>
                    Unit Price: Rs.{item.unitPrice.toFixed(2)}
                  </div>
                  <div className='text-gray-600'>
                    Total Price: Rs.{item.totalPrice.toFixed(2)}
                  </div>
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </>
  )
}

export default DisplayOrderDetail
