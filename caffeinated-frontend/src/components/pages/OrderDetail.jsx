import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchOrderDetails } from '../../store/slices/orderSlice'
import DisplayEmpty from './Empty'
import DisplayOrderDetail from './DisplayOrderDetail'

const OrderDetail = () => {
  const dispatch = useDispatch()
  const { data, operationStatus, error } = useSelector((state) => state.order)

  useEffect(() => {
    dispatch(fetchOrderDetails())
  }, [dispatch])

  console.log('state order', data)

  return (
    <div className='mt-24'>
      {data && data.orderDetails && data.orderDetails.length === 0 ? (
        <DisplayEmpty displayText='There are no orders to display' />
      ) : (
        <>
          <h1 className='font-kalnia font-semibold text-2xl p-4 text-center'>
            Your order details
          </h1>
          {data.orderDetails
            .slice()
            .sort((a, b) => new Date(b.orderedOn) - new Date(a.orderedOn)) // Sort by orderedOn in descending order
            .map((order) => (
              <DisplayOrderDetail key={order.orderId} order={order} />
            ))}
        </>
      )}
    </div>
  )
}

export default OrderDetail
