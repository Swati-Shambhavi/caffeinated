import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom"
import { fetchAllProducts } from "../store/slices/productSlice";
import DisplayProducts from "./DisplayProducts";
import { setSelectedCategory } from "../store/slices/categorySlice";

const Products = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const {categoryId} = useParams();
  const {selectedCategory} = useSelector(state => state.categories)
  const [filterBasedOnCategory, setFilterBasedOnCategory] = useState(false)
  const products = useSelector(state=> state.products)

  useEffect(()=>{
    if(categoryId != null){
      if(selectedCategory == null){
        dispatch(setSelectedCategory(category));
      }
      console.log("it also worked")
      setFilterBasedOnCategory(true)
    }else{
      console.log("No category selected so triggering the fetchAllProduct action")
      dispatch(fetchAllProducts())
    
    }
  },[dispatch])


 const handleAddProduct = () =>{
  
 }
    
  return (
    <>
    <h1>Inside Products page</h1>
  {filterBasedOnCategory ? 
    <DisplayProducts categoryFilterOn={true} categoriedProducts={selectedCategory.products}/> : <DisplayProducts categoryFilterOn={false} products={products}/>}
  <Link to="/products/addProduct">
    <button onClick={handleAddProduct}>Add Product</button>    
    <button onClick={()=>{navigate(-1)}}>Go back</button>
    </Link>
    </>
  )
}

export default Products