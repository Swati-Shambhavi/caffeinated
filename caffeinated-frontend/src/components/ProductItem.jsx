import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import { fetchProductItem } from '../store/slices/productSlice'

const ProductItem = () => {
    const {productId} = useParams()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const  { data, error, loading } = useSelector(state => state.product)
    useEffect(()=>{
        dispatch(fetchProductItem(productId))
    },[dispatch, productId])
    useEffect(()=>{console.log("Product=",data)},[data])
  return (<>
    <div>ProductItem {productId} </div>        
  <div>{data.name}</div>
 <button onClick={()=>{navigate(-1)}}>Back</button>
 </>
  )
}

export default ProductItem