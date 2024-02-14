import React from 'react'
import { useNavigate } from 'react-router-dom'
const DisplayProducts = ({categoryFilterOn,categoriedProducts, products}) => {
  const navigate = useNavigate()
  return (
    <div>{categoryFilterOn ? (<div className="">    
    <div>{categoriedProducts && categoriedProducts.length == 0 && <h2>No products in stock for this category</h2>}</div>
    <ul>
    {categoriedProducts.map(product => {
      return <li key={product.id} className="card" onClick={()=>navigate(`/products/${product.id}`)}> 
        <h4>{product.name}</h4>
        <p>{product.description}</p>
        <br/>
      </li>
    })}
    </ul> 
    </div>):(
        <div>
          {products.operationStatus === 'pending' ? (
            <h2>Fetching Products details...</h2>
          ) : (
            <ul>
              {Object.values(products.data).map((product) => (
                <li key={product.id} className="card" onClick={() => navigate(`/products/${product.id}`)}>
                  <h4>{product.name}</h4>
                  <p>{product.description}</p>
                  <br />
                </li>
              ))}
            </ul>
          )}
        </div>
      )}</div>
  )
}

export default DisplayProducts