import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { fetchProductItem, deleteProduct } from '../store/slices/productSlice'

const ProductItem = () => {
    const {productId} = useParams()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const  { data, error, operationStatus } = useSelector(state => state.products)
    useEffect(()=>{
        dispatch(fetchProductItem(productId))
    },[dispatch, productId])
    
    const handleDelete = (productId) =>{
      dispatch(deleteProduct({productId: productId}))
      navigate(-1)
    }
  return (<>
 
    <div>ProductItem {productId} </div> 
    {/* {!data && operationStatus === 'pending' && <h3>Loading....</h3>}        */}
  <div>{data.name}</div>
 <button onClick={()=>navigate(-1)}>Back</button>
 <button onClick={()=>deleteProduct(productId)} >Delete</button>
 <Link to={`/products/updateProduct/${productId}`}>
  <button>Update</button>
 </Link>
 


 </>
  )
}

export default ProductItem