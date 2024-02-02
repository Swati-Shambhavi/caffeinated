import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom"

const Products = () => {
  const navigate = useNavigate();
  const {selectedCategory} = useSelector(state => state.categories) 

 const handleViewProduct=(productId) =>{
    navigate(`/products/${productId}`)
 }
    
  return (<>
  
    <h2 className="text-2xl text-yellow-500"> {selectedCategory.name} Products</h2>

    <div>{selectedCategory.products.length == 0 && <h2>No products in stock for this category</h2>}</div>
    <ul>
      {selectedCategory.products.map(product => {
        return <li key={product.id} className="card" onClick={()=>{handleViewProduct(product.id)}}> 
          <h4>{product.name}</h4>
          <p>{product.description}</p>
          <br/>
        </li>
      })}
    </ul>
    <button onClick={()=>{navigate(-1)}}>Go back</button>
    </>
  )
}

export default Products