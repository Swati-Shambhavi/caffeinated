import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

import axios from 'axios'

export const fetchCartDetails = createAsyncThunk(
  'cart/fetchCartDetails',
  async (_, { getState }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      console.log('inside cart slice', userId)
      if (!userId) {
        throw new Error('User id not found.')
      }

      const accessToken = user.accessToken
      const url = `http://localhost:8080/caffeinated/carts/api/${encodeURIComponent(
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
      console.error('Error fetching cart details:', error)
      throw new Error('Error fetching cart details. Please try again.')
    }
  }
)

export const addToCart = createAsyncThunk(
  'cart/addToCart',
  async (cartDetail, { getState }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      if (!userId) {
        throw new Error('User id not found.')
      }

      const accessToken = user.accessToken
      const url = `http://localhost:8080/caffeinated/carts/api/${encodeURIComponent(
        userId
      )}`

      const response = await axios.post(url, cartDetail, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
      })

      console.log('raw response', response)

      if (response.status >= 200 && response.status < 300) {
        console.log('Response data in addToCart', response.data)
        return response.data
      }
      console.error('Error response from server:', response.data)
      throw new Error(`HTTP error! Status: ${response.status}`)
    } catch (error) {
      console.error('Error adding to cart:', error)
      throw new Error('Error adding to cart. Please try again.')
    }
  }
)

export const removeFromCart = createAsyncThunk(
  'cart/removeFromCart',
  async (cartDetail, { getState }) => {
    try {
      const { user } = getState()
      const userId = user.userProfile?.id
      if (!userId) {
        throw new Error('User id not found.')
      }

      const productId =
        typeof cartDetail.productId === 'string'
          ? parseInt(cartDetail.productId, 10)
          : cartDetail.productId

      const accessToken = user.accessToken
      const url = `http://localhost:8080/caffeinated/carts/api/${encodeURIComponent(
        userId
      )}`

      const response = await axios.delete(url, {
        data: productId,
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
      })

      console.log('raw response', response)

      if (response.status >= 200 && response.status < 300) {
        console.log('Response data in removeFromCart', response.data)
        return response.data
      }
      console.error('Error response from server:', response.data)
      throw new Error(`HTTP error! Status: ${response.status}`)
    } catch (error) {
      console.error('Error removing from cart:', error)
      throw new Error('Error removing from cart. Please try again.')
    }
  }
)

export const updateCartUser = createAsyncThunk(
  'cart/updateCartUser',
  async (userDetail, { getState }) => {
    try {
      const { user } = getState()
      console.log('user', user)
      const userEmail = user.userAuth?.email
      if (!userEmail) {
        throw new Error('User email not found.')
      }

      const accessToken = user.accessToken
      const url = `http://localhost:8080/caffeinated/users/api/${encodeURIComponent(
        userEmail
      )}`

      const response = await axios.put(url, userDetail, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
      })

      console.log('raw response', response)

      if (response.status !== 200) {
        console.error('Error response from server:', response.data)
        throw new Error(`HTTP error! Status: ${response.status}`)
      }

      return response.data
    } catch (error) {
      console.error('Error updating cart user:', error)
      throw new Error('Error updating cart user. Please try again.')
    }
  }
)

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    data: {
      id: '',
      cartItems: [],
      totalPrice: '',
      userId: '',
    },
    operationStatus: '',
    error: null,
  },
  reducers: {
    clearCart: (state) => {
      state.data.cartItems = []
      state.data.totalPrice = ''
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCartDetails.pending, (state) => {
        state.operationStatus = 'pending'
      })
      .addCase(fetchCartDetails.rejected, (state, action) => {
        state.operationStatus = 'rejected'
        state.error = action.error.message
      })
      .addCase(fetchCartDetails.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        console.log('in addCase', action.payload.data)
        state.data = action.payload.data
      })
      .addCase(addToCart.pending, (state) => {
        state.operationStatus = 'pending'
      })
      .addCase(addToCart.rejected, (state, action) => {
        state.operationStatus = 'rejected'
        state.error = action.error.message
      })
      .addCase(addToCart.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        console.log('in addCase', action.payload.data)
        state.data = action.payload.data
      })
      .addCase(removeFromCart.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        console.log('in remove from cart case', action.payload.data)
        state.data = action.payload.data
      })
      .addCase(updateCartUser.fulfilled, (state, action) => {
        state.operationStatus = 'fulfilled'
        state.data = action.payload.data
      })
  },
})

export const { clearCart } = cartSlice.actions
export default cartSlice.reducer
