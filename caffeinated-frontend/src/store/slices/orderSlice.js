import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from 'axios'
import { clearCart } from './cartSlice'

export const fetchOrderDetails = createAsyncThunk(
  'cart/fetchOrderDetails',
  async (_, { getState }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      console.log('inside order slice', userId)
      if (!userId) {
        throw new Error('User id not found.')
      }

      const accessToken = user.accessToken
      const url = `http://localhost:8080/caffeinated/orders/api/fetch/${encodeURIComponent(
        userId
      )}`

      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })

      console.log('raw response', response)

      if (response.status !== 200) {
        console.error('Error response from server:', response.data)
        throw new Error(`HTTP error! Status: ${response.status}`)
      }

      return response.data
    } catch (error) {
      console.error('Error fetching order details:', error)
      throw new Error('Error fetching order details. Please try again.')
    }
  }
)

export const createOrder = createAsyncThunk(
  'order/createOrder',
  async (totalAmount, { getState }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      if (!userId) {
        throw new Error('User id not found.')
      }

      const accessToken = user.accessToken
      const response = await axios.post(
        `http://localhost:8080/caffeinated/orders/api/create-order/${encodeURIComponent(
          userId
        )}`,
        {
          amount: totalAmount * 100,
          currency: 'INR',
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      console.log('Raw response from createOrder', response)

      if (response.status >= 200 && response.status < 300) {
        console.log('Response data in createOrder', response.data)
        return response.data
      }
      console.error('Error response from server:', response.data)
      throw new Error(`HTTP error! Status: ${response.status}`)
    } catch (error) {
      console.error('Error creating order:', error)
      throw new Error('Error creating order. Please try again.')
    }
  }
)
export const sendPaymentConfirmation = createAsyncThunk(
  'order/sendPaymentConfirmation',
  async (rezorpayResponse, { getState, dispatch }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      if (!userId) {
        throw new Error('User id not found.')
      }

      const accessToken = user.accessToken
      const response = await axios.post(
        `http://localhost:8080/caffeinated/orders/api/confirm-order/${encodeURIComponent(
          userId
        )}`,
        rezorpayResponse,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      console.log('Raw response from createOrder', response)

      if (response.status >= 200 && response.status < 300) {
        console.log('Response data in createOrder', response.data)
        dispatch(clearCart())
        return response.data
      }
      console.error('Error response from server:', response.data)
      throw new Error(`HTTP error! Status: ${response.status}`)
    } catch (error) {
      console.error('Error creating order:', error)
      throw new Error('Error creating order. Please try again.')
    }
  }
)

export const orderSlice = createSlice({
  name: 'order',
  initialState: {
    data: {
      isOrderSuccess: false,
      orderDetails: [],
    },
    operationStatus: '',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(createOrder.pending, (state) => {
        state.operationStatus = 'pending'
      })
      .addCase(createOrder.rejected, (state, action) => {
        state.operationStatus = 'rejected'
        state.error = action.error.message
      })
      .addCase(createOrder.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        state.data = action.payload.data
      })
      .addCase(sendPaymentConfirmation.pending, (state) => {
        state.operationStatus = 'pending'
      })
      .addCase(sendPaymentConfirmation.rejected, (state, action) => {
        state.operationStatus = 'rejected'
        state.error = action.error.message
      })
      .addCase(sendPaymentConfirmation.fulfilled, (state) => {
        state.operationStatus = 'fulfilled'
      })
      .addCase(fetchOrderDetails.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        state.data.orderDetails = action.payload.data
      })
  },
})
export default orderSlice.reducer
